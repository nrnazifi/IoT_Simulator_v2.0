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
                    <p class="mb-s mt-0 text-secondary">new sim</p>
                </vaadin-vertical-layout>
                <vaadin-horizontal-layout class="items-center justify-between" theme="on spacing-s">
                    <vaadin-button theme="primary" id="btnSaveRun">Save and Run</vaadin-button>
                    <vaadin-button theme="primary" id="btnSave">Save</vaadin-button>
                    <vaadin-button theme="error primary" id="btnDelete">Delete</vaadin-button>
                </vaadin-horizontal-layout>
            </vaadin-horizontal-layout>
            <vaadin-form-layout style="width: 100%;">
                <vaadin-text-field label="Name" id="name"></vaadin-text-field>
                <vaadin-combo-box label="Parking lot" id="parkingLots"></vaadin-combo-box>
                <vaadin-number-field label="Time Unit" id="timeUnit" helper-text="The factor defines how much real time passes with each step of simulation time"></vaadin-number-field>
                <span id="calculationTimeUnit">A unit of simulation time will take</span>
               
                <hr colspan="2"/>
                <vaadin-radio-group id="schedulingRadio" label="Scheduling" theme="horizontal" helper-text="You could run this simulation right away or in future"></vaadin-radio-group>
                <vaadin-date-time-picker id="schedulingDateTime"></vaadin-date-time-picker>
                
                <hr colspan="2"/>
                <vaadin-checkbox-group id="storageCheckBox" colspan="2" label="Store Data" theme="horizontal">
                </vaadin-checkbox-group>
                <vaadin-details id="storageDetails" colspan="2">
                    <div slot="summary">Network Configuration</div>
                    <vaadin-text-field id="endpointUri" label="Endpoint URL/IP" helper-text="e.g. broker.test.com:1883 or 196.168.0.1"></vaadin-text-field>
                    <vaadin-radio-group id="protocolRadio" label="Protocol" theme="horizontal"></vaadin-radio-group>
                    <vaadin-radio-group id="dataFormatRadio" label="Data Format" theme="horizontal"></vaadin-radio-group>
                </vaadin-details>

                <hr colspan="2"/>
                <vaadin-text-area colspan="2" label="Description" id="description"></vaadin-text-area>
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