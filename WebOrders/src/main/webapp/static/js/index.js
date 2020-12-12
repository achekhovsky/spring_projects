import * as temp from './templates.js';
import * as gutils from './GuiUtils.js';
import * as innational from './i18nApp.js';

window.i18next = innational.i18next;
window.doAction = gutils.doAction;
window.setHideRdyMode = gutils.setHideRdyMode;
window.findEmptyInputs = gutils.findEmptyInputs;
window.changeFile = gutils.changeFile;
window.separateObjectsAndStringify = temp.separateObjectsAndStringify;
window.Order = temp.Order;
window.OrderImage = temp.OrderImage;
window.Actions = temp.Actions;
