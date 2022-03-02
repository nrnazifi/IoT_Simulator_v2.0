import { html, LitElement, customElement } from 'lit-element';
import '@vaadin/vaadin-form-layout';
import '@vaadin/vaadin-text-field/vaadin-text-area';
import '@vaadin/vaadin-text-field/vaadin-text-field';
import '@vaadin/vaadin-text-field/vaadin-integer-field';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-button';

@customElement('parking-form-view')
export class ParkingFormView extends LitElement {
    createRenderRoot() {
        // Do not use a shadow root
        return this;
    }

    render() {
        return html`
        <main class="w-full max-w-screen-lg mx-auto pb-l px-l">
        <vaadin-horizontal-layout class="items-center justify-between">
            <vaadin-vertical-layout>
                <h2 class="mb-0 mt-xl text-3xl">Parking Template Form</h2>
                <p class="mb-xl mt-0 text-secondary">new lot</p>
            </vaadin-vertical-layout>
            <vaadin-horizontal-layout class="items-center justify-between" theme="on spacing-s">
                <vaadin-button theme="primary contrast" id="btnShowSpots">Show Spots</vaadin-button>
                <vaadin-button theme="success primary" id="btnGenerate">[Re]Generate Spots</vaadin-button>
                <vaadin-button theme="primary" id="btnSave">Save</vaadin-button>
                <vaadin-button theme="error primary" id="btnDelete">Delete</vaadin-button>
            </vaadin-horizontal-layout>
        </vaadin-horizontal-layout>
        <vaadin-form-layout style="width: 100%;">
            <vaadin-text-field label="Name" id="name"></vaadin-text-field>
            <vaadin-integer-field label="Capacity" id="capacity"></vaadin-integer-field>
            <vaadin-integer-field label="Start Id" id="startId"></vaadin-integer-field>
            <vaadin-integer-field label="occupied Number" id="numberOfOccupiedAtStart"></vaadin-integer-field>
            <vaadin-text-area label="Description" id="description"></vaadin-text-area>
        </vaadin-form-layout>
        <vaadin-horizontal-layout style="margin-top: var(--lumo-space-m); margin-bottom: var(--lumo-space-l);" theme="spacing">
            <vaadin-button id="cancel">
                Cancel 
            </vaadin-button>
        </vaadin-horizontal-layout>
        </main>
`;
    }
}