var ORDERS;
var hideRdy = false;

function getFormsInputValues(formId) {
	let formData = {};
	let node;
	node = document.querySelectorAll("form[id = '" + formId + "'] input");
	if(node.length == 0) {
		node = document.querySelectorAll("input[form='" + formId + "']");
	}
	node.forEach(
			function(item) {
				if(item.type == "checkbox") {
					formData[item.name] = item.checked;
				} else {
					formData[item.name] = item.value;
				}
				}
			);
	return formData; 
}


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

function processParamsToSendd(action, formId) {
	let frmData;
	if(formId) {
		frmData = new FormData(document.getElementById(formId));
		let cbs = getCheckboxesValues(formId);
		for(let key in cbs) {
			frmData.set(key, cbs[key]);
		}
	}  else {
		frmData = new FormData();
	}
	frmData.append("getAction", action);
	frmData.append("hideRdy", hideRdy);
	return frmData;
}

function setHideRdyMode(mode) {
	if(typeof(mode) == "boolean") {
		hideRdy = mode;
	}
}

function doAction(url, action, formId) {
	$.ajax({
		type:"POST",
		url: url,
		processData: false, 
		contentType: false,
		data: processParamsToSendd(action, formId),
		success: function (data) {	
			console.log("doAction[" + action + "], orders:");
			for(let ordd in ORDERS) {
			let orderToString = "";
				for(let key in ORDERS[ordd]) {
					orderToString += "[" + key + "]" + ORDERS[ordd][key] + " ";	
				}
			console.log(orderToString + "\n");
			}
			console.log("<----data----");
			console.log(data);
			console.log("----data---->");
			if(data.length != 0) { 
  				ORDERS = JSON.parse(data);
			}
			setRows("orderstable", ORDERS);
		}});
		return false;
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

function getFormData(formId) {
	var obj = "";
	var jArray = $("#" + formId).serializeArray();
	$.each(jArray, function() {
		obj += this.name + "=" + this.value + "&";
	});
	return obj;
}

function clearTable(tableId) {
	document.getElementById(tableId).tBodies[0].innerHTML = "";
}

function setRows(tableId, orders) {
	clearTable(tableId);
	console.log("setRows:" + ORDERS);
	if(orders instanceof Array) {
		orders.forEach(function (item) {
			addRow(document.getElementById(tableId), item);
		});
	}
}

function addRow(table, order) {
	var row = document.createElement("tr");
	var frm = document.createElement("form");
	frm.id = order['id'];
	frm.name = "orders";
	frm.action = "orders";
	frm.method = "POST";
	row.insertAdjacentElement("beforeEnd", frm);
		for(key in order) {
			let td = document.createElement("td");
			let inp = document.createElement("input");
			inp.name = key;
			inp.value = order[key];
			inp.setAttribute('form', order['id']);
			if (key == "done") {
				inp.type = "checkbox";
				inp.checked = order[key];
				inp.addEventListener("change", function() {
					doAction("orders", "UPDATE", order['id']);
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
		btn.name = "getAction";
		btn.value = "deleteOrder"; 
		btn.type = "button";
		btn.formMethod = "post";
		btn.setAttribute('form', order['id']);
		btn.textContent = "Delete";
		btn.addEventListener("click", function() {
			doAction("orders", "DELETE", order['id']);
		});
		frm.insertAdjacentElement("beforeEnd", btn);
		td.insertAdjacentElement("beforeEnd", btn);
		row.insertAdjacentElement("beforeEnd", td);
	if(table.tBodies.length == 0) {
		table.insertAdjacentElement("beforeEnd", document.createElement("tbody"));
	} 
	table.tBodies[0].insertAdjacentElement("beforeEnd", row);
}

function stringToBuffer(string) {
	    var string = btoa(unescape(encodeURIComponent(string))),
	            charList = string.split(''),
		            buffer = [];
	        for (var i = 0; i < charList.length; i++) {
			        buffer.push(charList[i].charCodeAt(0));
				    }
		    return buffer;
}

function convertToImage(buffer, onLoad) {
  var mime;
  var a = new Uint8Array(buffer);
  var nb = a.length;
  if (nb < 4)
      return null;
  var b0 = a[0];
  var b1 = a[1];
  var b2 = a[2];
  var b3 = a[3];
  if (b0 == 0x89 && b1 == 0x50 && b2 == 0x4E && b3 == 0x47)
	  mime = 'image/png';
  else if (b0 == 0xff && b1 == 0xd8)
	  mime = 'image/jpeg';
  else if (b0 == 0x47 && b1 == 0x49 && b2 == 0x46)
      	  mime = 'image/gif';
  else
  return null;
  var binary = "";
  for (var i = 0; i < nb; i++)
 	 binary += String.fromCharCode(a[i]);
  var base64 = window.btoa(binary);
  var image = new Image();
  image.onload = onLoad;
  image.src = 'data:' + mime + ';base64,' + base64;
  return image;
}

function setFileListener(inpFile, img) {
  inpFile.addEventListener("change", function(event) {
    var i = 0,
    files = inpFile.files,
    len = files.length;
    for (; i < len; i++) {
      let fr = new FileReader();
      fr.addEventListener("load", function () {
	      img.src = fr.result;
      }, false);
      fr.readAsDataURL(files[0]);
    }                                                  
  }, false);
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
	      }, false);
	      fr.readAsDataURL(files[0]);                                                  
	  }
  }

