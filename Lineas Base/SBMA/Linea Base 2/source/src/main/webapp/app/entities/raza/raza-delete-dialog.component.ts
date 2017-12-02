import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Raza } from './raza.model';
import { RazaPopupService } from './raza-popup.service';
import { RazaService } from './raza.service';

@Component({
    selector: 'jhi-raza-delete-dialog',
    templateUrl: './raza-delete-dialog.component.html'
})
export class RazaDeleteDialogComponent {

    raza: Raza;

    constructor(
        private razaService: RazaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.razaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'razaListModification',
                content: 'Deleted an raza'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-raza-delete-popup',
    template: ''
})
export class RazaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private razaPopupService: RazaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.razaPopupService
                .open(RazaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
