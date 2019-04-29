import { element, by, promise, ElementFinder } from 'protractor';

export class ClientsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-clients div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ClientsUpdatePage {
    pageTitle = element(by.id('jhi-clients-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomInput = element(by.id('field_nom'));
    dateAbonnementInput = element(by.id('field_dateAbonnement'));
    provinceInput = element(by.id('field_province'));
    villeInput = element(by.id('field_ville'));
    adresseInput = element(by.id('field_adresse'));
    passwordInput = element(by.id('field_password'));
    typesSelect = element(by.id('field_types'));
    telephoneInput = element(by.id('field_telephone'));
    emailInput = element(by.id('field_email'));
    rccmInput = element(by.id('field_rccm'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setNomInput(nom): promise.Promise<void> {
        return this.nomInput.sendKeys(nom);
    }

    getNomInput() {
        return this.nomInput.getAttribute('value');
    }

    setDateAbonnementInput(dateAbonnement): promise.Promise<void> {
        return this.dateAbonnementInput.sendKeys(dateAbonnement);
    }

    getDateAbonnementInput() {
        return this.dateAbonnementInput.getAttribute('value');
    }

    setProvinceInput(province): promise.Promise<void> {
        return this.provinceInput.sendKeys(province);
    }

    getProvinceInput() {
        return this.provinceInput.getAttribute('value');
    }

    setVilleInput(ville): promise.Promise<void> {
        return this.villeInput.sendKeys(ville);
    }

    getVilleInput() {
        return this.villeInput.getAttribute('value');
    }

    setAdresseInput(adresse): promise.Promise<void> {
        return this.adresseInput.sendKeys(adresse);
    }

    getAdresseInput() {
        return this.adresseInput.getAttribute('value');
    }

    setPasswordInput(password): promise.Promise<void> {
        return this.passwordInput.sendKeys(password);
    }

    getPasswordInput() {
        return this.passwordInput.getAttribute('value');
    }

    setTypesSelect(types): promise.Promise<void> {
        return this.typesSelect.sendKeys(types);
    }

    getTypesSelect() {
        return this.typesSelect.element(by.css('option:checked')).getText();
    }

    typesSelectLastOption(): promise.Promise<void> {
        return this.typesSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }
    setTelephoneInput(telephone): promise.Promise<void> {
        return this.telephoneInput.sendKeys(telephone);
    }

    getTelephoneInput() {
        return this.telephoneInput.getAttribute('value');
    }

    setEmailInput(email): promise.Promise<void> {
        return this.emailInput.sendKeys(email);
    }

    getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    setRccmInput(rccm): promise.Promise<void> {
        return this.rccmInput.sendKeys(rccm);
    }

    getRccmInput() {
        return this.rccmInput.getAttribute('value');
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
