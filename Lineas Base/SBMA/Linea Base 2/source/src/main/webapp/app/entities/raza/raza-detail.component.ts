import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Raza } from './raza.model';
import { RazaService } from './raza.service';

@Component({
    selector: 'jhi-raza-detail',
    templateUrl: './raza-detail.component.html'
})
export class RazaDetailComponent implements OnInit, OnDestroy {

    raza: Raza;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private razaService: RazaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRazas();
    }

    load(id) {
        this.razaService.find(id).subscribe((raza) => {
            this.raza = raza;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRazas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'razaListModification',
            (response) => this.load(this.raza.id)
        );
    }
}
