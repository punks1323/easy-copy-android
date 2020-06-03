package com.easycopy.databinding;
import com.easycopy.R;
import com.easycopy.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMainBindingImpl extends ActivityMainBinding implements com.easycopy.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    @NonNull
    private final android.widget.Button mboundView2;
    @NonNull
    private final android.widget.TextView mboundView3;
    @NonNull
    private final android.widget.Button mboundView4;
    @NonNull
    private final android.widget.Button mboundView5;
    @NonNull
    private final android.widget.Button mboundView6;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    @Nullable
    private final android.view.View.OnClickListener mCallback4;
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener mboundView1androidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.selectedDir.get()
            //         is viewModel.selectedDir.set((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(mboundView1);
            // localize variables for thread safety
            // viewModel.selectedDir
            androidx.databinding.ObservableField<java.lang.String> viewModelSelectedDir = null;
            // viewModel
            com.easycopy.screen.home.HomeViewModel viewModel = mViewModel;
            // viewModel.selectedDir.get()
            java.lang.String viewModelSelectedDirGet = null;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.selectedDir != null
            boolean viewModelSelectedDirJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelSelectedDir = viewModel.getSelectedDir();

                viewModelSelectedDirJavaLangObjectNull = (viewModelSelectedDir) != (null);
                if (viewModelSelectedDirJavaLangObjectNull) {




                    viewModelSelectedDir.set(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener mboundView3androidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.get()
            //         is viewModel.state.set((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(mboundView3);
            // localize variables for thread safety
            // viewModel.state
            androidx.databinding.ObservableField<java.lang.String> viewModelState = null;
            // viewModel
            com.easycopy.screen.home.HomeViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state.get()
            java.lang.String viewModelStateGet = null;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.set(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public ActivityMainBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private ActivityMainBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.Button) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (android.widget.TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView4 = (android.widget.Button) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (android.widget.Button) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView6 = (android.widget.Button) bindings[6];
        this.mboundView6.setTag(null);
        setRootTag(root);
        // listeners
        mCallback3 = new com.easycopy.generated.callback.OnClickListener(this, 3);
        mCallback4 = new com.easycopy.generated.callback.OnClickListener(this, 4);
        mCallback1 = new com.easycopy.generated.callback.OnClickListener(this, 1);
        mCallback2 = new com.easycopy.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.viewModel == variableId) {
            setViewModel((com.easycopy.screen.home.HomeViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.easycopy.screen.home.HomeViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelState((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 1 :
                return onChangeViewModelSelectedDir((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(androidx.databinding.ObservableField<java.lang.String> ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelSelectedDir(androidx.databinding.ObservableField<java.lang.String> ViewModelSelectedDir, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        boolean viewModelStateEqualsViewModelCONNECTED = false;
        boolean ViewModelStateEqualsViewModelCONNECTED1 = false;
        androidx.databinding.ObservableField<java.lang.String> viewModelState = null;
        androidx.databinding.ObservableField<java.lang.String> viewModelSelectedDir = null;
        com.easycopy.screen.home.HomeViewModel viewModel = mViewModel;
        java.lang.String viewModelSelectedDirGet = null;
        java.lang.String viewModelStateGet = null;

        if ((dirtyFlags & 0xfL) != 0) {


            if ((dirtyFlags & 0xdL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.state
                        viewModelState = viewModel.getState();
                    }
                    updateRegistration(0, viewModelState);


                    if (viewModelState != null) {
                        // read viewModel.state.get()
                        viewModelStateGet = viewModelState.get();
                    }


                    if (viewModelStateGet != null) {
                        // read viewModel.state.get().equals(HomeViewModel.CONNECTED)
                        ViewModelStateEqualsViewModelCONNECTED1 = viewModelStateGet.equals(com.easycopy.screen.home.HomeViewModel.CONNECTED);
                    }


                    // read !viewModel.state.get().equals(HomeViewModel.CONNECTED)
                    viewModelStateEqualsViewModelCONNECTED = !ViewModelStateEqualsViewModelCONNECTED1;
            }
            if ((dirtyFlags & 0xeL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.selectedDir
                        viewModelSelectedDir = viewModel.getSelectedDir();
                    }
                    updateRegistration(1, viewModelSelectedDir);


                    if (viewModelSelectedDir != null) {
                        // read viewModel.selectedDir.get()
                        viewModelSelectedDirGet = viewModelSelectedDir.get();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0xeL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, viewModelSelectedDirGet);
        }
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.mboundView1, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, mboundView1androidTextAttrChanged);
            this.mboundView2.setOnClickListener(mCallback1);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.mboundView3, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, mboundView3androidTextAttrChanged);
            this.mboundView4.setOnClickListener(mCallback2);
            this.mboundView5.setOnClickListener(mCallback3);
            this.mboundView6.setOnClickListener(mCallback4);
        }
        if ((dirtyFlags & 0xdL) != 0) {
            // api target 1

            this.mboundView2.setEnabled(viewModelStateEqualsViewModelCONNECTED);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, viewModelStateGet);
            this.mboundView4.setEnabled(viewModelStateEqualsViewModelCONNECTED);
            this.mboundView5.setEnabled(ViewModelStateEqualsViewModelCONNECTED1);
            this.mboundView6.setEnabled(ViewModelStateEqualsViewModelCONNECTED1);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 3: {
                // localize variables for thread safety
                // viewModel
                com.easycopy.screen.home.HomeViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    viewModel.openFile();
                }
                break;
            }
            case 4: {
                // localize variables for thread safety
                // viewModel
                com.easycopy.screen.home.HomeViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    viewModel.disconnect();
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // viewModel
                com.easycopy.screen.home.HomeViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    viewModel.choseRootDir();
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // viewModel
                com.easycopy.screen.home.HomeViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    viewModel.connect();
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.state
        flag 1 (0x2L): viewModel.selectedDir
        flag 2 (0x3L): viewModel
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}