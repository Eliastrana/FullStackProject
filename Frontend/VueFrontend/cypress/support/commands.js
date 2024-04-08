Cypress.Commands.add('login', (username, password) => {
  cy.request({
    method: 'POST',
    url: 'http://localhost:8080/api/user/login',
    body: {
      username,
      password
    }
  }).then((resp) => {
    window.sessionStorage.setItem('token', resp.body.token);
  });
});

