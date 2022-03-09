import { html, LitElement, customElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-select';

@customElement('simulation-view-card')
export class SimulationViewCard extends LitElement {
    createRenderRoot() {
        // Do not use a shadow root
        return this;
    }

    render() {
        return html`
    <li class="bg-contrast-5 flex flex-col items-start p-s rounded-l">
        <vaadin-horizontal-layout theme="spacing padding" class="height-5xl" style="align-items: flex-start;">
            <div class="flex items-center justify-center overflow-hidden rounded-m">
                <img id="image" class="w-xl" src="images/simulation.png">
            </div>
            <vaadin-vertical-layout id="header_section" class="height-5xl" style="align-items: stretch" theme="spacing-s">
                <span class="text-xl font-semibold" id="header"></span>
                <span class="text-s text-secondary" id="subtitle"></span>
            </vaadin-vertical-layout>
        </vaadin-horizontal-layout>
        <p class="my-m" id="text"></p>
        <div class="panel-footer w-full">
            <vaadin-horizontal-layout theme="spacing-s" class="actions" style="align-items: center; justify-content: flex-end">
                <span id="edit" title="Edit"><iron-icon class="icon" icon="vaadin:pencil"></iron-icon></span>
                <span id="delete" title="Delete"><iron-icon class="icon" icon="vaadin:trash"></iron-icon></span>
            </vaadin-horizontal-layout>
        </div>
    </li>
`;
    }
}
