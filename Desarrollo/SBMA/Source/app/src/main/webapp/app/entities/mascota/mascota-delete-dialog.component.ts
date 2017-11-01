import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Mascota } from './mascota.model';
import { MascotaPopupService } from './mascota-popup.service';
import { MascotaService } from './mascota.service';

@Component({
    selector: 'jhi-mascota-delete-dialog',
    templateUrl: './mascota-delete-dialog.component.html'
})
export class MascotaDeleteDialogComponent {

    mascota: Mascota;

    constructor(
        private mascotaService: MascotaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mascotaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mascotaListModification',
                content: 'Deleted an mascota'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mascota-delete-popup',
    template: ''
})
export class MascotaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mascotaPopupService: MascotaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mascotaPopupService
                .open(MascotaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
