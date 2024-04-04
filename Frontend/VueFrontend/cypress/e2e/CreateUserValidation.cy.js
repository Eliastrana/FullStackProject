describe('User Registration', () => {
  it('successfully registers a new user', () => {
    cy.visit('/create-user');

    // Fyller inn registreringsskjemaet
    cy.get('input[id="username"]').type('TestBruker');
    cy.get('input[id="email"]').type('TestBruker@test.com');
    cy.get('input[id="password"]').type('test123');
    cy.get('input[id="password-confirmation"]').type('Test123');

    cy.get('button[id="register-user"]').should('not.be.disabled');
  });

  it('validates passwords match before submitting form', () => {
    cy.visit('/create-user');

    // Fyller inn registreringsskjemaet med ulike passord
    cy.get('input[id="username"]').type('TestBruker');
    cy.get('input[id="email"]').type('TestBruker@test.com');
    cy.get('input[id="password"]').type('test123');
    cy.get('input[id="password-confirmation"]').type('annetpassord');

    // Forsøker å sende skjemaet
    cy.get('form').submit();
    cy.contains('Passwords do not match.');
  });
});
