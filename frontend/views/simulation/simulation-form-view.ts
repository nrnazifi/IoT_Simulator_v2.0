import { html, LitElement, customElement } from 'lit-element';
import '@vaadin/vaadin-form-layout';
import '@vaadin/vaadin-text-field/vaadin-text-area';
import '@vaadin/vaadin-text-field/vaadin-text-field';
import '@vaadin/vaadin-text-field/vaadin-number-field';
import '@vaadin/vaadin-combo-box';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-button';

@customElement('simulation-form-view')
export class SimulationFormView extends LitElement {
    createRenderRoot() {
        // Do not use a shadow root
        return this;
    }

    render() {
        return html`
        <main class="w-full max-w-screen-lg mx-auto pb-l px-s">
            <vaadin-horizontal-layout class="items-center justify-between">
                <vaadin-vertical-layout>
                    <h2 class="mb-0 mt-xl text-3xl">Simulation Form</h2>
                    <p class="mb-xl mt-0 text-secondary">new sim</p>
                </vaadin-vertical-layout>
                <vaadin-horizontal-layout class="items-center justify-between" theme="on spacing-s">
                    <vaadin-button theme="primary" id="btnSave">Save</vaadin-button>
                    <vaadin-button theme="error primary" id="btnDelete">Delete</vaadin-button>
                </vaadin-horizontal-layout>
            </vaadin-horizontal-layout>
            <vaadin-form-layout style="width: 100%;">
                <vaadin-text-field label="Name" id="name"></vaadin-text-field>
                <vaadin-combo-box label="Parking lot" id="parkingLots"></vaadin-combo-box>
                <vaadin-number-field label="Time Unit" id="timeUnit"></vaadin-number-field>
                <vaadin-text-area label="Description" id="description"></vaadin-text-area>
                <hr colspan="2"/>
                <vaadin-radio-group id="schedulingRadio" label="Scheduling" theme="horizontal">
                    <vaadin-radio-button value="now" label="Schedule Now" checked>Schedule Now</vaadin-radio-button>
                    <vaadin-radio-button value="later" label="Schedule Later">Schedule Later</vaadin-radio-button>
                </vaadin-radio-group>
                <vaadin-date-time-picker id="schedulingDateTime"></vaadin-date-time-picker>
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