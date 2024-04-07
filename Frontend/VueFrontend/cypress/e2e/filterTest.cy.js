describe('Testing for the filters on quizzes page', () => {
  it('Should be able to filter quizzes by category', () => {
    //login to user to access the quizzes page
    cy.visit('http://localhost:5173/login');
    cy.get('input#username').type('testbruker');
    cy.get('input#password').type('password1');
    cy.get('button#sign-in').click();
    cy.url().should('include', '/MyAccount');

    cy.visit('http://localhost:5173/Quizzes');
    cy.get('select').eq(0).select('EASY');
  });
   it('Should be able to filter quizzes by category', () => {
     //login to user to access the quizzes page
     cy.visit('http://localhost:5173/login');
     cy.get('input#username').type('testbruker');
     cy.get('input#password').type('password1');
     cy.get('button#sign-in').click();
     cy.url().should('include', '/MyAccount');

     cy.visit('http://localhost:5173/Quizzes');
     cy.get('select').eq(1).select('Geography');
   });

   it('Should be able to filter by searchign for tags', () => {
     //login to user to access the quizzes page
     cy.visit('http://localhost:5173/login');
     cy.get('input#username').type('testbruker');
     cy.get('input#password').type('password1');
     cy.get('button#sign-in').click();
     cy.url().should('include', '/MyAccount');

     cy.visit('http://localhost:5173/Quizzes');
     cy.get('input').type('test');
   });
});