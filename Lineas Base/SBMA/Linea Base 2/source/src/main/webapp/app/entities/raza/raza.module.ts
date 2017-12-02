import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    RazaService,
    RazaPopupService,
    RazaComponent,
    RazaDetailComponent,
    RazaDialogComponent,
    RazaPopupComponent,
    RazaDeletePopupComponent,
    RazaDeleteDialogComponent,
    razaRoute,
    razaPopupRoute,
    RazaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...razaRoute,
    ...razaPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RazaComponent,
        RazaDetailComponent,
        RazaDialogComponent,
        RazaDeleteDialogComponent,
        RazaPopupComponent,
        RazaDeletePopupComponent,
    ],
    entryComponents: [
        RazaComponent,
        RazaDialogComponent,
        RazaPopupComponent,
        RazaDeleteDialogComponent,
        RazaDeletePopupComponent,
    ],
    providers: [
        RazaService,
        RazaPopupService,
        RazaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppRazaModule {}
