```javascript
/// <reference types="cypress" />

describe('Salesforce Login', () => {
    it('should login to Salesforce', () => {
        // Open Salesforce Login Page
        cy.visit('https://login.salesforce.com/', { ensure: 'domcontentloaded' });

        // Locate Username field and enter value
        cy.get('#username').type('your_username');

        // Locate Password field and enter value
        cy.get('#password').type('your_password');

        // Locate Remember Me checkbox and select it (if needed)
        cy.get('#rememberUn').check({ force: true });

        // Locate and click Login button
        cy.get('#Login').click();

        // Print the title of the new page after login
        cy.title().then((title) => {
            cy.log('Page Title: ' + title);
        });
    });
});
```