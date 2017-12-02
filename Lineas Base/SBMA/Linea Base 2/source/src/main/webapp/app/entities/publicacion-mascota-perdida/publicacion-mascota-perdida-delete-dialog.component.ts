import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PublicacionMascotaPerdida } from './publicacion-mascota-perdida.model';
import { PublicacionMascotaPerdidaPopupService } from './publicacion-mascota-perdida-popup.service';
import { PublicacionMascotaPerdidaService } from './publicacion-mascota-perdida.service';

@Component({
    selector: 'jhi-publicacion-mascota-perdida-delete-dialog',
    templateUrl: './publicacion-mascota-perdida-delete-dialog.component.html'
})
export class PublicacionMascotaPerdidaDeleteDialogComponent {

    publicacionMascotaPerdida: PublicacionMascotaPerdida;

    constructor(
        private publicacionMascotaPerdidaService: PublicacionMascotaPerdidaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.publicacionMascotaPerdidaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'publicacionMascotaPerdidaListModification',
                content: 'Deleted an publicacionMascotaPerdida'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-publicacion-mascota-perdida-delete-popup',
    template: ''
})
export class PublicacionMascotaPerdidaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private publicacionMascotaPerdidaPopupService: PublicacionMascotaPerdidaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.publicacionMascotaPerdidaPopupService
                .open(PublicacionMascotaPerdidaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
