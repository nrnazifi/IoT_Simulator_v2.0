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
<h3>Parking Configuration</h3>
<vaadin-form-layout style="width: 100%;">
 <vaadin-text-field label="Name" id="name"></vaadin-text-field>
 <vaadin-integer-field label="Capacity" id="capacity"></vaadin-integer-field>
 <vaadin-integer-field label="occupied Number" id="numberOfOccupiedAtStart"></vaadin-integer-field>
 <vaadin-text-area label="Description" id="description"></vaadin-text-area>
</vaadin-form-layout>
<vaadin-horizontal-layout style="margin-top: var(--lumo-space-m); margin-bottom: var(--lumo-space-l);" theme="spacing">
 <vaadin-button theme="primary" id="save">
   Save 
 </vaadin-button>
 <vaadin-button id="cancel">
   Cancel 
 </vaadin-button>
</vaadin-horizontal-layout>
`;
    }
}