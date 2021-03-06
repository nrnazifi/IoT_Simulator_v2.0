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
                <vaadin-button theme="primary" id="btnSave">Save</vaadin-button>
                <vaadin-button theme="error primary" id="btnDelete">Delete</vaadin-button>
            </vaadin-horizontal-layout>
        </vaadin-horizontal-layout>
        <vaadin-form-layout style="width: 100%;">
            <vaadin-text-field label="Name" id="name"></vaadin-text-field>
            <vaadin-integer-field label="Capacity" id="capacity"></vaadin-integer-field>
            <vaadin-integer-field label="Start Id" id="startId" helper-text="Spot Id starts with this number"></vaadin-integer-field>
            <vaadin-integer-field label="Number of Occupied spots" id="numberOfOccupiedAtStart" 
                                  helper-text="How many spots are occupied by default when the simulator is started?"></vaadin-integer-field>
            <vaadin-text-area label="Description" id="description"></vaadin-text-area>

            <hr colspan="2"/>
            <vaadin-custom-field label="Time of daylight and darkness" id="daylight_darkness" helper-text="These are used for the following configurations">
                <vaadin-time-picker id="daylight" label="Daylight" .step="${60 * 30}" auto-open-disabled></vaadin-time-picker>
                <vaadin-time-picker id="darkness" label="Darkness" .step="${60 * 30}" auto-open-disabled></vaadin-time-picker>
            </vaadin-custom-field>

            <hr colspan="2"/>
            <vaadin-radio-group colspan="2" id="occupiedRadio" 
                                label="On average, how long does each vehicle stay in the parking lot?" theme="horizontal">
            </vaadin-radio-group>
            <span colspan="2" id="generallyOccupiedDetail"></span>
            <span colspan="2" id="workRestTimeOccupiedDetail"></span>
            <span colspan="2" id="weekdaysOccupiedDetail"></span>
            
            <hr colspan="2"/>
            <vaadin-radio-group colspan="2" id="requestRadio"
                                label="On average, how many spot statuses are changed in each period?" theme="horizontal">
            </vaadin-radio-group>
            <span colspan="2" id="generallyRequestDetail"></span>
            <span colspan="2" id="workRestTimeRequestDetail"></span>
            <span colspan="2" id="weekdaysRequestDetail"></span>
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