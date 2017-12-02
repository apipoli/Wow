import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    ProvinciaService,
    ProvinciaPopupService,
    ProvinciaComponent,
    ProvinciaDetailComponent,
    ProvinciaDialogComponent,
    ProvinciaPopupComponent,
    ProvinciaDeletePopupComponent,
    ProvinciaDeleteDialogComponent,
    provinciaRoute,
    provinciaPopupRoute,
    ProvinciaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...provinciaRoute,
    ...provinciaPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProvinciaComponent,
        ProvinciaDetailComponent,
        ProvinciaDialogComponent,
        ProvinciaDeleteDialogComponent,
        ProvinciaPopupComponent,
        ProvinciaDeletePopupComponent,
    ],
    entryComponents: [
        ProvinciaComponent,
        ProvinciaDialogComponent,
        ProvinciaPopupComponent,
        ProvinciaDeleteDialogComponent,
        ProvinciaDeletePopupComponent,
    ],
    providers: [
        ProvinciaService,
        ProvinciaPopupService,
        ProvinciaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProvinciaModule {}
