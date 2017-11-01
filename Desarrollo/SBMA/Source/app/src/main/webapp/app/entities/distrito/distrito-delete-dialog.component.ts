import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Distrito } from './distrito.model';
import { DistritoPopupService } from './distrito-popup.service';
import { DistritoService } from './distrito.service';

@Component({
    selector: 'jhi-distrito-delete-dialog',
    templateUrl: './distrito-delete-dialog.component.html'
})
export class DistritoDeleteDialogComponent {

    distrito: Distrito;

    constructor(
        private distritoService: DistritoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.distritoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'distritoListModification',
                content: 'Deleted an distrito'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-distrito-delete-popup',
    template: ''
})
export class DistritoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private distritoPopupService: DistritoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.distritoPopupService
                .open(DistritoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
