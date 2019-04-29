import { element, by, promise, ElementFinder } from 'protractor';

export class VoituresComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-voitures div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class VoituresUpdatePage {
    pageTitle = element(by.id('jhi-voitures-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    plaqueInput = element(by.id('field_plaque'));
    marqueInput = element(by.id('field_marque'));
    couleurInput = element(by.id('field_couleur'));
    devicesSelect = element(by.id('field_devices'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setPlaqueInput(plaque): promise.Promise<void> {
        return this.plaqueInput.sendKeys(plaque);
    }

    getPlaqueInput() {
        return this.plaqueInput.getAttribute('value');
    }

    setMarqueInput(marque): promise.Promise<void> {
        return this.marqueInput.sendKeys(marque);
    }

    getMarqueInput() {
        return this.marqueInput.getAttribute('value');
    }

    setCouleurInput(couleur): promise.Promise<void> {
        return this.couleurInput.sendKeys(couleur);
    }

    getCouleurInput() {
        return this.couleurInput.getAttribute('value');
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
