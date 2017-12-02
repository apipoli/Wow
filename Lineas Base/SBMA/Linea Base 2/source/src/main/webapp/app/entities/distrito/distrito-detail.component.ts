import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Distrito } from './distrito.model';
import { DistritoService } from './distrito.service';

@Component({
    selector: 'jhi-distrito-detail',
    templateUrl: './distrito-detail.component.html'
})
export class DistritoDetailComponent implements OnInit, OnDestroy {

    distrito: Distrito;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private distritoService: DistritoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDistritos();
    }

    load(id) {
        this.distritoService.find(id).subscribe((distrito) => {
            this.distrito = distrito;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDistritos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'distritoListModification',
            (response) => this.load(this.distrito.id)
        );
    }
}
