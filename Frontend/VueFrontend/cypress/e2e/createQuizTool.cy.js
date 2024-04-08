describe('Create Quiz Tool Test', () => {
  it('Should create a quiz and send it to the backend', () => {
    // Intercept the POST request
    cy.intercept('POST', 'http://localhost:8080/api/completeQuiz').as('createQuizRequest');

    cy.visit('http://localhost:4173/Login');
    cy.get('input#username').type('user');
    cy.get('input#password').type('Password123');
    cy.get('button#sign-in').click();
    cy.url().should('include', '/');
    // eslint-disable-next-line cypress/no-unnecessary-waiting
    cy.wait(5000);


    cy.visit('http://localhost:4173/quizcreator');
    cy.url().should('include', '/quizcreator');

    // Input the quiz details
    cy.get('#quiz-title-input').type('Cypress Quiz Test');
    cy.get('textarea').first().type('This is a Cypress test quiz');
    cy.get('select').eq(0).select('Geography');
    cy.get('select').eq(1).select('Easy');

    // Input question details
    cy.get('.quiz-type-buttons button').contains('Multiple Choice').click();
    cy.get('.question-input').type('What is the capital of France?');
    cy.get('.answer-group').eq(0).find('.question-answer').type('Paris');
    cy.get('.answer-group').eq(0).find('.question-checkbox').check();
    cy.get('#add-answers').click();
    cy.get('.answer-group').eq(1).find('.question-answer').type('London');
    cy.get('#add-answers').click();
    cy.get('.answer-group').eq(2).find('.question-answer').type('Oslo');
    cy.get('#add-answers').click();
    cy.get('.answer-group').eq(3).find('.question-answer').type('Rome');

    cy.get('.tag-input').type('test');

    // Submit the quiz
    cy.get('button').contains('Save Quiz').click();

    // Wait for the POST request to be made and check the response
    cy.wait('@createQuizRequest').then((interception) => {
      expect(interception.response.statusCode).to.eq(201);

    });
  });
});
