/*
 * Copyright 2017-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.koin.androidx.viewmodel.ext.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.viewmodel.ViewModelOwner.Companion.from
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.androidx.viewmodel.scope.emptyState
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.KoinScopeComponent
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import kotlin.reflect.KClass

/**
 * ComponentActivity extensions to help for ViewModel
 *
 * @author Arnaud Giuliani
 */
@Deprecated("getStateViewModel will be merged to getViewModel")
inline fun <reified T : ViewModel> SavedStateRegistryOwner.stateViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition = emptyState(),
    noinline parameters: ParametersDefinition? = null,
): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        getStateViewModel(qualifier, state, parameters)
    }
}
@Deprecated("getStateViewModel will be merged to getViewModel")
fun <T : ViewModel> SavedStateRegistryOwner.stateViewModel(
    qualifier: Qualifier? = null,
    state: BundleDefinition = emptyState(),
    clazz: KClass<T>,
    parameters: ParametersDefinition? = null,
): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) { getStateViewModel(qualifier, state, clazz, parameters) }
}

@Deprecated("getStateViewModel will be merged to getViewModel")
inline fun <reified T : ViewModel> SavedStateRegistryOwner.getStateViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition = emptyState(),
    noinline parameters: ParametersDefinition? = null,
): T {
    return getStateViewModel(qualifier, state, T::class, parameters)
}

@OptIn(KoinInternalApi::class)
@Deprecated("getStateViewModel will be merged to getViewModel")
fun <T : ViewModel> SavedStateRegistryOwner.getStateViewModel(
    qualifier: Qualifier? = null,
    state: BundleDefinition = emptyState(),
    clazz: KClass<T>,
    parameters: ParametersDefinition? = null,
): T {
    val owner = { from(this as ViewModelStoreOwner, this) }
    val scope = getKoinScope(this)
    return scope.getViewModel(qualifier, state, owner, clazz, parameters)
}