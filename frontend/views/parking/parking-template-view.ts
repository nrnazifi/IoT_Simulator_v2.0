import { html, LitElement, customElement } from 'lit-element';
import '@vaadin/vaadin-grid/all-imports';
import '@vaadin/vaadin-grid-pro';
import '@vaadin/vaadin-grid-pro/vaadin-grid-pro-edit-column';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-text-field';



@customElement('parking-template-view')
export class ParkingTemplateView extends LitElement {
    createRenderRoot() {
        // Do not use a shadow root
        return this;
    }

    render() {
        return html`<vaadin-grid-pro id="grid" theme="no-border column-borders"> </vaadin-grid-pro>`;
    }
}
