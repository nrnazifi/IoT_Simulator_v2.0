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
            <vaadin-integer-field label="Start Id" id="startId" helper-text="Spot Id starts with this number"></vaadin-integer-field>
            <vaadin-integer-field label="Number of Occupied spots" id="numberOfOccupiedAtStart" 
                                  helper-text="How many spots are occupied by default when the simulator is started?"></vaadin-integer-field>
            <vaadin-text-area label="Description" id="description"></vaadin-text-area>

            <hr colspan="2"/>
            <vaadin-radio-group colspan="2" id="occupiedRadio" 
                                label="On average, how long does each vehicle stay in the parking lot?" theme="horizontal">
            </vaadin-radio-group>
            <span colspan="2" id="generallyOccupiedDetail">
                <vaadin-custom-field label="General average">
                    <vaadin-integer-field id="averageOccupied_day" placeholder="0" style="width: 5em"></vaadin-integer-field>
                    <vaadin-integer-field id="averageOccupied_night" placeholder="0" style="width: 5em"></vaadin-integer-field>
                </vaadin-custom-field>
            </span>
            <span colspan="2" id="workRestTimeOccupiedDetail">
                <vaadin-custom-field label="Work days">
                    <vaadin-integer-field id="workdayOccupied_day" placeholder="0" style="width: 5em"></vaadin-integer-field>
                    <vaadin-integer-field id="workdayOccupied_night" placeholder="0" style="width: 5em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Weekends">
                    <vaadin-integer-field id="weekendOccupied_day" placeholder="0" style="width: 5em"></vaadin-integer-field>
                    <vaadin-integer-field id="weekendOccupied_night" placeholder="0" style="width: 5em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Holidays">
                    <vaadin-integer-field id="holidayOccupied_day" placeholder="0" style="width: 5em"></vaadin-integer-field>
                    <vaadin-integer-field id="holidayOccupied_night" placeholder="0" style="width: 5em"></vaadin-integer-field>
                </vaadin-custom-field>
            </span>
            <span colspan="2" id="weekdaysOccupiedDetail">
                <vaadin-custom-field label="Monday">
                    <vaadin-integer-field id="mondayOccupied_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="mondayOccupied_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Tuesday">
                    <vaadin-integer-field id="tuesdayOccupied_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="tuesdayOccupied_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Wednesday">
                    <vaadin-integer-field id="wednesdayOccupied_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="wednesdayOccupied_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Thursday">
                    <vaadin-integer-field id="thursdayOccupied_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="thursdayOccupied_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Friday">
                    <vaadin-integer-field id="fridayOccupied_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="fridayOccupied_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Saturday">
                    <vaadin-integer-field id="saturdayOccupied_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="saturdayOccupied_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Sunday">
                    <vaadin-integer-field id="sundayOccupied_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="sundayOccupied_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>
            </span>
            
            <hr colspan="2"/>
            <vaadin-radio-group colspan="2" id="requestRadio"
                                label="On average, how many spot statuses are changed in each period?" theme="horizontal">
            </vaadin-radio-group>
            <span colspan="2" id="generallyRequestDetail">
                <vaadin-custom-field label="General average">
                    <vaadin-integer-field id="averageRequest_day" placeholder="0" style="width: 5em"></vaadin-integer-field>
                    <vaadin-integer-field id="averageRequest_night" placeholder="0" style="width: 5em"></vaadin-integer-field>
                </vaadin-custom-field>
            </span>
            <span colspan="2" id="workRestTimeRequestDetail">
                <vaadin-custom-field label="Work days">
                    <vaadin-integer-field id="workdayRequest_day" placeholder="0" style="width: 5em"></vaadin-integer-field>
                    <vaadin-integer-field id="workdayRequest_night" placeholder="0" style="width: 5em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Weekends">
                    <vaadin-integer-field id="weekendRequest_day" placeholder="0" style="width: 5em"></vaadin-integer-field>
                    <vaadin-integer-field id="weekendRequest_night" placeholder="0" style="width: 5em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Holidays">
                    <vaadin-integer-field id="holidayRequest_day" placeholder="0" style="width: 5em"></vaadin-integer-field>
                    <vaadin-integer-field id="holidayRequest_night" placeholder="0" style="width: 5em"></vaadin-integer-field>
                </vaadin-custom-field>
            </span>
            <span colspan="2" id="weekdaysRequestDetail">
                <vaadin-custom-field label="Monday">
                    <vaadin-integer-field id="mondayRequest_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="mondayRequest_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Tuesday">
                    <vaadin-integer-field id="tuesdayRequest_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="tuesdayRequest_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Wednesday">
                    <vaadin-integer-field id="wednesdayRequest_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="wednesdayRequest_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Thursday">
                    <vaadin-integer-field id="thursdayRequest_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="thursdayRequest_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Friday">
                    <vaadin-integer-field id="fridayRequest_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="fridayRequest_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Saturday">
                    <vaadin-integer-field id="saturdayRequest_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="saturdayRequest_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>&ndash;
                <vaadin-custom-field label="Sunday">
                    <vaadin-integer-field id="sundayRequest_day" placeholder="0" style="width: 4em"></vaadin-integer-field>
                    <vaadin-integer-field id="sundayRequest_night" placeholder="0" style="width: 4em"></vaadin-integer-field>
                </vaadin-custom-field>
            </span>
            
            <hr colspan="2"/>
            <vaadin-custom-field label="Time of daylight and darkness" id="daylight_darkness">
                <vaadin-text-field id="daylight" label="Daylight" placeholder="06:00" style="width: 10em"></vaadin-text-field>
                <vaadin-text-field id="darkness" label="Darkness" placeholder="18:00" style="width: 10em"></vaadin-text-field>
            </vaadin-custom-field>
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