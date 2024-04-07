describe('Login Validation', () => {
  beforeEach(() => {
    cy.visit('http://localhost:5173/Login',)
  });
  it('login button should not be disabled when input is empty', () => {
    cy.get('input[id="username"]').type('testbruker');
    cy.get('input[id="password"]').type('testpassord');
    // Sjekk at "Sign in" knappen ikke er disabeled lenger
    cy.get('button[id="sign-in"]').should('not.be.disabled');
  });
  it('login button should be disabled when input is empty', () => {
    // Sjekk at "Sign in" knappen er disabeled
    cy.get('button[id="sign-in"]').should('be.disabled');
  });
  it('login button should be disabled when username is empty', () => {
    cy.get('input[id="password"]').type('testpassord');
    // Sjekk at "Sign in" knappen er disabeled
    cy.get('button[id="sign-in"]').should('be.disabled');
  });
  it('login button should be disabled when password is empty', () => {
    cy.get('input[id="username"]').type('testbruker');
    // Sjekk at "Sign in" knappen er disabeled
    cy.get('button[id="sign-in"]').should('be.disabled');
  });
  it('displays an error message for wrong username or password', () => {
    cy.visit('/login');
    cy.get('input[id="username"]').type('feilbruker');
    cy.get('input[id="password"]').type('feilpassord');
    cy.get('button[id="sign-in"]').click();
    cy.contains('Incorrect username or password.').should('be.visible');
  });

  it('redirects user to the MyAccount page on successful login', () => {
    cy.visit('/login');

    cy.get('input[type="text"]').type('test');
    cy.get('input[type="password"]').type('test');
    cy.get('form').submit();
    
    cy.url().should('include', '/MyAccount');

  });

});