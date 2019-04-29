import { element, by, promise, ElementFinder } from 'protractor';

export class DevicesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-devices div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DevicesUpdatePage {
    pageTitle = element(by.id('jhi-devices-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    imeiInput = element(by.id('field_imei'));
    numeroSimInput = element(by.id('field_numeroSim'));
    clientsSelect = element(by.id('field_clients'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setImeiInput(imei): promise.Promise<void> {
        return this.imeiInput.sendKeys(imei);
    }

    getImeiInput() {
        return this.imeiInput.getAttribute('value');
    }

    setNumeroSimInput(numeroSim): promise.Promise<void> {
        return this.numeroSimInput.sendKeys(numeroSim);
    }

    getNumeroSimInput() {
        return this.numeroSimInput.getAttribute('value');
    }

    clientsSelectLastOption(): promise.Promise<void> {
        return this.clientsSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    clientsSelectOption(option): promise.Promise<void> {
        return this.clientsSelect.sendKeys(option);
    }

    getClientsSelect(): ElementFinder {
        return this.clientsSelect;
    }

    getClientsSelectedOption() {
        return this.clientsSelect.element(by.css('option:checked')).getText();
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
