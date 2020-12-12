export {doAction, setHideRdyMode, findEmptyInputs, changeFile};

let ORDERS;
let hideRdy = false;


// <!-- private -->
function getCheckboxesValues(formId) {
	let formCheckboxes = {};
	let node;
	node = document.querySelectorAll("form[id = '" + formId + "'] input");
	if(node.length == 0) {
		node = document.querySelectorAll("input[form='" + formId + "']");
	}
	node.forEach(
			function(item) {
				if(item.type == "checkbox") {
					formCheckboxes[item.name] = item.checked;
				} 
			});
	return formCheckboxes; 
}


async function processParamsToSendd(action, formId) {
	let frmData;
	if(formId) {
		frmData = convertFormDataCheckboxes(formId);
		for(let key of frmData.keys()) {
			if(frmData.get(key) instanceof File) {
				const promiseFile = convertFileToBlob(frmData.get(key));
				const loadedFile = await promiseFile;
				frmData.append("img", loadedFile);
				frmData.delete(key);
			}
		}
	}  else {
		frmData = new FormData();
	}
	frmData.append("action", action);
	frmData.append("hideRdy", hideRdy);
	return frmData;
}


function convertFormDataCheckboxes(formId) {
	let frmData = new FormData(document.getElementById(formId));
	let cbs = getCheckboxesValues(formId);
	for(let key in cbs) {
		frmData.set(key, cbs[key]);
	}
	return frmData;
}

function convertFileToBlob(file) {
	return  new Promise((resolve, reject) => {
		let reader = new FileReader();
		reader.onload = function() {
			resolve(window.btoa(reader.result));
		};
		reader.onerror = function() {
			reject("error");
		};
		reader.readAsBinaryString(file);
	})
	.then(
		(r) => {
			return r;
		}, 
		(e) => {
			return e;
	});
}


function clearTable(tableId) {
	document.getElementById(tableId).tBodies[0].innerHTML = "";
}

function setRows(tableId, orders, url) {
	clearTable(tableId);
	if(orders instanceof Array) {
		orders.forEach(function (item) {
			addRow(document.getElementById(tableId), item, url);
		});
	}
}

function addRow(table, order, url) {
	var row = document.createElement("tr");
	var frm = document.createElement("form");
	frm.id = order['id'];
	frm.name = "orders";
	frm.action = "orders";
	frm.method = "POST";
	row.insertAdjacentElement("beforeEnd", frm);
		for(let key in order) {
			let td = document.createElement("td");
			let inp = document.createElement("input");
			inp.name = key;
			inp.value = order[key];
			inp.setAttribute('form', order['id']);
			if (key == "done") {
				inp.type = "checkbox";
				inp.checked = order[key];
				inp.addEventListener("change", function() {
					doAction(url, "UPDATE", order['id']);
				});
			} else if (key == "image") {
				inp = document.createElement("img"); 
				inp.width = "35px";
				inp.height = "35px";
				inp.style.width = "35px";
				inp.style.height = "35px";
				inp.src = 'data:image/png;base64,' + order[key].img;
			}
			else {
				inp.type = "hidden";
				td.textContent = order[key];
			}
			td.insertAdjacentElement("beforeEnd", inp);
			row.insertAdjacentElement("beforeEnd", td);
		}
		let td = document.createElement("td");
		td.style.textAlign = "right";
		let btn = document.createElement("button");
		btn.className = "btn btn-outline-danger btn-sm";
		btn.name = "action";
		btn.value = "deleteOrder"; 
		btn.type = "button";
		btn.formMethod = "post";
		btn.setAttribute('form', order['id']);
		btn.setAttribute('data-i18n', 'orders.table.body.deleteButton');
		//btn.textContent = "Delete";
		btn.addEventListener("click", function() {
			doAction(url, "DELETE", order['id']);
		});
		frm.insertAdjacentElement("beforeEnd", btn);
		td.insertAdjacentElement("beforeEnd", btn);
		row.insertAdjacentElement("beforeEnd", td);
	if(table.tBodies.length == 0) {
		table.insertAdjacentElement("beforeEnd", document.createElement("tbody"));
	} 
	table.tBodies[0].insertAdjacentElement("beforeEnd", row);
}


// <!-- public -->
function doAction(url, action, formId) {
	processParamsToSendd(action, formId).then((result) => {
		$.ajax({
			type:"POST",
			url: url,
			processData: false, 
			contentType: "application/json;charset=utf-8",
			data: separateObjectsAndStringify(
				Object.fromEntries(result.entries()),
				new Order(), 
				new OrderImage(),
				new Actions()),
			success: function (data) {	
				for(let ordd in ORDERS) {
				let orderToString = "";
					for(let key in ORDERS[ordd]) {
						orderToString += "[" + key + "]" + ORDERS[ordd][key] + " ";	
					}
				}
				if(data.length != 0) { 
					ORDERS = data;
				} else {
					ORDERS = null;	
				}
				setRows("orderstable", ORDERS, url);
				$("*[data-i18n]").localize();
		}});
	});
}

function setHideRdyMode(mode) {
	if(typeof(mode) == "boolean") {
		hideRdy = mode;
	}
}

function findEmptyInputs() {
	let isEmtpy = false;
	let inputs = document.getElementsByTagName("input");
	for (let i = 0; i < inputs.length; i++) {
		if(inputs[i].getAttribute("type") == "text" && inputs[i].value == "") {
			isEmtpy = true;
			break;
		}
	}
	return isEmtpy;
}

function changeFile(event, imgId) {
    let i = 0,
    files = event.currentTarget.files,
    len = files.length,
    img = document.getElementById(imgId);
    for (; i < len; i++) {
      let fr = new FileReader();
      fr.addEventListener("load", function () {
	      img.src = fr.result;
	      img.image = fr.result;
	      img.img = fr.result;
	      img.orderImage = fr.result;
      }, false);
      fr.readAsDataURL(files[0]);                                                  
  }
}
