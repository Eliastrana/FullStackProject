# VueFrontend

This template should help get you started developing with Vue 3 in Vite.

___

### Table of contents

- [VueFrontend](#vuefrontend)
  - [Table of contents](#table-of-contents)
- [Recommended IDE Setup](#recommended-ide-setup)
- [Customize configuration](#customize-configuration)
- [Project Setup](#project-setup)
  - [Clone the project](#clone-the-project)
  - [Install Npm](#install-npm)
  - [Compile and Hot-Reload for Development](#compile-and-hot-reload-for-development)
  - [Compile and Minify for Production](#compile-and-minify-for-production)
  - [Run Unit Tests with Vitest](#run-unit-tests-with-vitest)
  - [Run End-to-End Tests with Cypress](#run-end-to-end-tests-with-cypress)
  - [Lint with ESLint](#lint-with-eslint)
- [Test Users](#test-users)  
- [Contact Information](#contact-information)

---

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Project Setup

### Clone the project
```bash
git clone git@github.com:Eliastrana/FullStackProject.git
```
```bash
cd SpringbootBackend
```
### Install Npm
```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```
NB: you need to have the [Backend](https://github.com/Eliastrana/FullStackProject/tree/main/Backend/SpringbootBackend) running to be able to log in and use the application.

### Compile and Minify for Production

```
npm run build
``` 

### Run Unit Tests with [Vitest](https://vitest.dev/)

```sh
npm run test:unit
```

### Run End-to-End Tests with [Cypress](https://www.cypress.io/)

```sh
npm run test:e2e:dev
```

This runs the end-to-end tests against the Vite development server.
It is much faster than the production build.

But it's still recommended to test the production build with `test:e2e` before deploying (e.g. in CI environments):

```sh
npm run build
npm run test:e2e
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```
---
## Test Users
### Admin
- Username: admin
- Password: Password123

### User
- Username: user
- Password: Password123
---
## Contact Information

If You have trouble running the project, questions or feedback, feel free to contact us at:

- [Support](mailto:support@quand.no)
- [Feedback](mailto:support@quand.no)
