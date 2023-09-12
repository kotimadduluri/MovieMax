# MovieMax
Movie Max application built by using android latest development tools


         Login         |       Register        |       Movies        |      Details

<img src="docs/login.png " width="180" height="380" /> <img src="docs/signup.png " width="180" height="380" /> <img src="docs/Movies_list.png " width="180" height="380" /> <img src="docs/movie.png " width="180" height="380" />


# Project Structure

<img src="docs/project_structure.png " width="380" height="720" />


# Build

The app contains the usual `debug` and `release` build variants.

The app also uses
[product flavors](https://developer.android.com/studio/build/build-variants#product-flavors) to
control where content for the app should be loaded from.

The `dev` flavor uses static local data to allow immediate building and exploring of the UI.

The `prod` flavor makes real network calls to a backend server, providing up-to-date content. At
this time, there is not a public backend available.

For normal development use the `devDebug` | `devRelease` based on needs

For normal development use the `prodRelease` variant.

# TechStack you must know
[JetPack Compose](https://insert-koin.io/docs/setup/koin) For designing

[Meterial](https://m3.material.io/) MDS

[ViewModel](https://developer.android.com/reference/androidx/lifecycle/ViewModel) MVVM

[Koin](https://insert-koin.io/docs/setup/koin) For Dependency injection

[Retrofit](https://square.github.io/retrofit/) For network calls

[Coil](https://square.github.io/retrofit/) For network images



# Testing

To facilitate testing of components, **MovieMax** uses dependency injection
[Koin](https://insert-koin.io/docs/setup/koin).

Most data layer components are defined as interfaces.
Then, concrete implementations (with various dependencies) are bound to provide those interfaces to
other components in the app.
In tests, **MovieMax** notably does _not_ use any mocking libraries.
Instead, We use fake repositories by using Koin



