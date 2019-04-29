import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { STrackingSharedModule } from 'app/shared';
import {
    DevicesComponent,
    DevicesDetailComponent,
    DevicesUpdateComponent,
    DevicesDeletePopupComponent,
    DevicesDeleteDialogComponent,
    devicesRoute,
    devicesPopupRoute
} from './';

const ENTITY_STATES = [...devicesRoute, ...devicesPopupRoute];

@NgModule({
    imports: [STrackingSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DevicesComponent,
        DevicesDetailComponent,
        DevicesUpdateComponent,
        DevicesDeleteDialogComponent,
        DevicesDeletePopupComponent
    ],
    entryComponents: [DevicesComponent, DevicesUpdateComponent, DevicesDeleteDialogComponent, DevicesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class STrackingDevicesModule {}
