package com.easycopy.screen.home;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import androidx.databinding.ObservableField;
import androidx.documentfile.provider.DocumentFile;

import com.easycopy.core.di.qualifier.ApplicationContext;
import com.easycopy.data.DataManager;
import com.easycopy.screen.base.BaseViewModel;
import com.easycopy.screen.home.model.DirInfo;
import com.easycopy.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.ByteString;
import timber.log.Timber;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-01
 */
public class HomeViewModel extends BaseViewModel<HomeNavigator> {


    public static final int REQUEST_CODE_READ_FILE = 6666;
    public static final int REQUEST_CODE_READ_DIR_TREE = 6667;
    public static final String CONNECTED = "CONNECTED";
    public static final String DISCONNECTED = "DISCONNECTED";
    public static final String FAILED = "FAILED";

    private ObservableField<String> state = new ObservableField<>();
    private ObservableField<String> selectedDir = new ObservableField<>();
    private WebSocket ws;

    private DataManager dataManager;

    @Inject
    public HomeViewModel(DataManager dataManager) {
        this.dataManager = dataManager;
        Timber.w("CONSTRUCTOR");
    }

    public ObservableField<String> getState() {
        return state;
    }

    public void setState(ObservableField<String> state) {
        this.state = state;
    }

    public ObservableField<String> getSelectedDir() {
        return selectedDir;
    }

    public void setSelectedDir(ObservableField<String> selectedDir) {
        this.selectedDir = selectedDir;
    }

    public void choseRootDir() {
        getNavigator().openDirectory(REQUEST_CODE_READ_DIR_TREE);
    }

    public void connect() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .writeTimeout(5, TimeUnit.MINUTES)
                .callTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(httpLoggingInterceptor);

        OkHttpClient client = builder.build();

        Request request = new Request.Builder().url("ws://192.168.0.108:8080/binary").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    public void disconnect() {
        ws.close(EchoWebSocketListener.NORMAL_CLOSURE_STATUS, "Goodbye !");
    }

    public void openFile() {
        getNavigator().openFile(REQUEST_CODE_READ_FILE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultData, ContentResolver contentResolver) {
        try {

            if (requestCode == REQUEST_CODE_READ_FILE && resultCode == Activity.RESULT_OK) {
                if (resultData != null) {
                    Uri uri = resultData.getData();

                    ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

                    FileInputStream fileInputStream = new FileInputStream(fileDescriptor);

                    ByteArrayOutputStream os = new ByteArrayOutputStream();

                    byte[] buffer = new byte[1024];
                    int len;

                    while ((len = fileInputStream.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                    }

                    fileInputStream.close();
                    parcelFileDescriptor.close();

                    Timber.i("Writing started....");
                    ws.send(ByteString.of(os.toByteArray()));
                    Timber.i("Writing completed....");

                }
            } else if (requestCode == REQUEST_CODE_READ_DIR_TREE && resultCode == Activity.RESULT_OK) {
                if (resultData != null) {
                    Uri treeUri = resultData.getData();
                    DocumentFile pickedDir = getNavigator().getDocumentFile(treeUri);

                    dataManager.setSelectedDir(pickedDir.getName());
                    dataManager.setSelectedDirUri(pickedDir.getUri().toString());
                    init();

                    DirInfo dirInfo = new DirInfo();
                    dirInfo.setParentDirName(pickedDir.getName());
                    dirInfo.setFiles(new ArrayList<>());
                    for (DocumentFile documentFile : pickedDir.listFiles()) {

                        DirInfo.FileInfo fileInfo = new DirInfo.FileInfo();
                        fileInfo.setIsDirectory(documentFile.isDirectory());
                        fileInfo.setIsFile(documentFile.isFile());
                        fileInfo.setName(documentFile.getName());
                        fileInfo.setUri(documentFile.getUri().toString());
                        fileInfo.setLastModified(documentFile.lastModified());
                        fileInfo.setLength(documentFile.length());

                        dirInfo.getFiles().add(fileInfo);
                    }

                    Timber.w("JSON:: " + new ObjectMapper().writeValueAsString(dirInfo));

                }
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public void init() {
        String selectedPath = dataManager.getSelectedDir();
        getSelectedDir().set(StringUtils.isEmpty(selectedPath) ? "" : selectedPath);
    }

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            getState().set(CONNECTED);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Timber.w("Receiving : " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            Timber.w("Receiving bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            Timber.w("onClosing");
            //webSocket.close(NORMAL_CLOSURE_STATUS, null);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Timber.w("Error : " + t.getMessage());
            getState().set(FAILED);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            Timber.w("onClosed");
            getState().set(DISCONNECTED);

        }
    }
}
