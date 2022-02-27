import { html, LitElement, customElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-select';

@customElement('parking-template-view-card')
export class ParkingTemplateViewCard extends LitElement {
    createRenderRoot() {
        // Do not use a shadow root
        return this;
    }

    render() {
        return html`
    <li class="bg-contrast-5 flex flex-col items-start p-s rounded-l">
        <vaadin-horizontal-layout theme="spacing padding" class="height-5xl" style="align-items: flex-start;">
            <div class="flex items-center justify-center overflow-hidden rounded-m">
                <img id="image" class="w-xl" src="images/parking-sensor.png">
            </div>
            <vaadin-vertical-layout id="header_section" class="height-5xl" style="align-items: stretch" theme="spacing-s">
                <span class="text-xl font-semibold" id="header"></span>
                <span class="text-s text-secondary" id="subtitle"></span>
            </vaadin-vertical-layout>
        </vaadin-horizontal-layout>
        <p class="my-m" id="text"></p>
        <div class="panel-footer w-full">
            <vaadin-horizontal-layout theme="spacing-s" class="actions" style="align-items: center; justify-content: flex-end">
                <a id="spotList" title="Show Spots"><iron-icon class="icon" icon="vaadin:list"></iron-icon></a>
                <a id="export" title="Export"><iron-icon class="icon" icon="vaadin:download"></iron-icon></a>
                <a id="duplicate" title="Duplicate"><iron-icon class="icon" icon="vaadin:copy-o"></iron-icon></a>
                <a id="edit" title="Edit"><iron-icon class="icon" icon="vaadin:pencil"></iron-icon></a>
                <a id="delete" title="Delete"><iron-icon class="icon" icon="vaadin:trash"></iron-icon></a>
            </vaadin-horizontal-layout>
        </div>
    </li>
`;
    }
}
