import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { STrackingSharedModule } from 'app/shared';
import {
    VoituresComponent,
    VoituresDetailComponent,
    VoituresUpdateComponent,
    VoituresDeletePopupComponent,
    VoituresDeleteDialogComponent,
    voituresRoute,
    voituresPopupRoute
} from './';

const ENTITY_STATES = [...voituresRoute, ...voituresPopupRoute];

@NgModule({
    imports: [STrackingSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VoituresComponent,
        VoituresDetailComponent,
        VoituresUpdateComponent,
        VoituresDeleteDialogComponent,
        VoituresDeletePopupComponent
    ],
    entryComponents: [VoituresComponent, VoituresUpdateComponent, VoituresDeleteDialogComponent, VoituresDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class STrackingVoituresModule {}
