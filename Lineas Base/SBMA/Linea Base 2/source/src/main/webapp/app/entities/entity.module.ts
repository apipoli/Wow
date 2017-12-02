import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AppRegionModule } from './region/region.module';
import { AppProvinciaModule } from './provincia/provincia.module';
import { AppDistritoModule } from './distrito/distrito.module';
import { AppRazaModule } from './raza/raza.module';
import { AppMascotaModule } from './mascota/mascota.module';
import { AppPublicacionMascotaPerdidaModule } from './publicacion-mascota-perdida/publicacion-mascota-perdida.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AppRegionModule,
        AppProvinciaModule,
        AppDistritoModule,
        AppRazaModule,
        AppMascotaModule,
        AppPublicacionMascotaPerdidaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEntityModule {}
