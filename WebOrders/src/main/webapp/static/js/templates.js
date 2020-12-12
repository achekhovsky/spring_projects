export {Order, OrderImage, Actions, separateObjectsAndStringify};

/**
 *
 * Scripts for filtering fields when creating a JSON request
 *
 */


// <!-- private -->
function getKeys(obj) {
    let keys = [];
    for(let key in obj) {
	if(obj[key] instanceof Object && !(obj[key] instanceof Array)) {
	    keys.push(key);
	    keys = keys.concat(getKeys(obj[key]));
	} else {    
	    keys.push(key);
	}
    }
    return keys;
}

// <!-- public -->
function Order(id = 0, createDate = new Date(), name = "", description = "", done = false) {
	//The name of the constructor must be the same (case sensitive) as the field
	//in the ObjectsWrapper.class 
	Object.defineProperty(Order.prototype.constructor, "name", {value: "order"});
	this.id = id; 
	this.createDate = createDate;
	this.name = name;
	this.description = description;
	this.done = done; 
}

function OrderImage(id = 0, image =  new Uint8Array(8), img = new Uint8Array(8)) {
	Object.defineProperty(OrderImage.prototype.constructor, "name", {value: "orderImage"});
	this.id = id; 
	this.image = image;
	this.img = {};
}

function Actions(action = "", hideRdy = false) {
	Object.defineProperty(Actions.prototype.constructor, "name", {value: "actions"});
	this.action = action;
	this.hideRdy = hideRdy;
}

function separateObjectsAndStringify(object, ...templates) {
	let separated = {};
	for(let template of templates) {
		separated[template.constructor.name] = {};
		for(let key of getKeys(template)) {
			if(object.hasOwnProperty(key)) {
				separated[template.constructor.name][key] = object[key]; 
			}	
		}
	}
	return JSON.stringify(separated);
}


