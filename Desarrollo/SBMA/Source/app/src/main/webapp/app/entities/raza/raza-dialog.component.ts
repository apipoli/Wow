import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Raza } from './raza.model';
import { RazaPopupService } from './raza-popup.service';
import { RazaService } from './raza.service';

@Component({
    selector: 'jhi-raza-dialog',
    templateUrl: './raza-dialog.component.html'
})
export class RazaDialogComponent implements OnInit {

    raza: Raza;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private razaService: RazaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.raza.id !== undefined) {
            this.subscribeToSaveResponse(
                this.razaService.update(this.raza));
        } else {
            this.subscribeToSaveResponse(
                this.razaService.create(this.raza));
        }
    }

    private subscribeToSaveResponse(result: Observable<Raza>) {
        result.subscribe((res: Raza) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Raza) {
        this.eventManager.broadcast({ name: 'razaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-raza-popup',
    template: ''
})
export class RazaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private razaPopupService: RazaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.razaPopupService
                    .open(RazaDialogComponent as Component, params['id']);
            } else {
                this.razaPopupService
                    .open(RazaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
