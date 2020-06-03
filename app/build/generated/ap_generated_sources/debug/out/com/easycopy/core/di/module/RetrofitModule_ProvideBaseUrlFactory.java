// Generated by Dagger (https://dagger.dev).
package com.easycopy.core.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class RetrofitModule_ProvideBaseUrlFactory implements Factory<String> {
  private final RetrofitModule module;

  public RetrofitModule_ProvideBaseUrlFactory(RetrofitModule module) {
    this.module = module;
  }

  @Override
  public String get() {
    return provideBaseUrl(module);
  }

  public static RetrofitModule_ProvideBaseUrlFactory create(RetrofitModule module) {
    return new RetrofitModule_ProvideBaseUrlFactory(module);
  }

  public static String provideBaseUrl(RetrofitModule instance) {
    return Preconditions.checkNotNull(instance.provideBaseUrl(), "Cannot return null from a non-@Nullable @Provides method");
  }
}