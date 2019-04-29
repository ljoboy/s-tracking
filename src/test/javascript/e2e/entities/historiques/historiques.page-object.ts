import { element, by, promise, ElementFinder } from 'protractor';

export class HistoriquesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-historiques div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class HistoriquesUpdatePage {
    pageTitle = element(by.id('jhi-historiques-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dateHistoriqueInput = element(by.id('field_dateHistorique'));
    longitudeInput = element(by.id('field_longitude'));
    latitudeInput = element(by.id('field_latitude'));
    devicesSelect = element(by.id('field_devices'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setDateHistoriqueInput(dateHistorique): promise.Promise<void> {
        return this.dateHistoriqueInput.sendKeys(dateHistorique);
    }

    getDateHistoriqueInput() {
        return this.dateHistoriqueInput.getAttribute('value');
    }

    setLongitudeInput(longitude): promise.Promise<void> {
        return this.longitudeInput.sendKeys(longitude);
    }

    getLongitudeInput() {
        return this.longitudeInput.getAttribute('value');
    }

    setLatitudeInput(latitude): promise.Promise<void> {
        return this.latitudeInput.sendKeys(latitude);
    }

    getLatitudeInput() {
        return this.latitudeInput.getAttribute('value');
    }

    devicesSelectLastOption(): promise.Promise<void> {
        return this.devicesSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    devicesSelectOption(option): promise.Promise<void> {
        return this.devicesSelect.sendKeys(option);
    }

    getDevicesSelect(): ElementFinder {
        return this.devicesSelect;
    }

    getDevicesSelectedOption() {
        return this.devicesSelect.element(by.css('option:checked')).getText();
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
