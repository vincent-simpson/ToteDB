
var rh = rh || {};
rh.mq = rh.mq || {};

rh.mq.attachEventHandlers = function() {
	$("#addBettingAreaForm").on('shown.bs.modal', function() {
		$("#defaultForm-betting-area").focus();

	});

	$("#modalAddMachineForm").on('shown.bs.modal', function() {
		$("#addMachine-lsnNumber").focus();

	});


};

$(document).ready(function() {
	rh.mq.attachEventHandlers();

});

function openBettingAreaModal(id) {
	$.ajax({
		type : 'POST',
		url : "/bettingAreas/edit?bettingAreaId=" + id,
		success : function(data) {
			var nomArea = data;

			$("#bettingAreaModalHolder").html(nomArea);

			$("#bettingAreaModalHolder #add-bettingArea-button").html("Save");
			$("#bettingAreaModalHolder #add-bettingArea-title").html("Edit Betting Area");

			$("#addBettingAreaForm").modal("show");



		}
	});
};

function bindParentButtonText(id) {


	console.log('id ' + id);

	if(id == 0) {
		id = -1;
	}

	// var id = -1;

	$.ajax({
		type : 'POST',
		url : "/bettingAreas/bindToModel?bettingAreaId=" + id,
		success : function(data) {
			console.log('success');
		}

	});
}


function openMachineModal(id) {		
	$.ajax({
		type : 'POST',
		url : "/machines/edit?machineId=" + id,
		success : function(data) {
			var nomArea = data;

			$("#machineModalHolder").html(nomArea);

			$("#machineModalHolder #add-machine-button").html("Save");
			$("#machineModalHolder #add-machine-title").html("Edit Machine");

			$("#modalAddMachineForm").modal("show");

		}
	});
}

function openNotesModal(id) {

	$.ajax({
		type: 'POST',
		url: '/machines/addNotes/bind?machineId=' + id,
		success : function(data) {
			var nomArea = data;

			$('#notesModalHolder').html(nomArea);

			$("#modalAddNotesForm").modal("show");
		},

		error : function(data) {
			console.log(data);
		}

	});
}

function receiveId(id) {
	console.log(id);
}

function deleteBettingArea(id) {
	if(window.confirm("Are you sure?")) {
		
		// need to check if notes input and date haven't been edited. if so, delete without sending ajax request.
		var 
		

		$.ajax({
			type : 'GET',
			url : "/bettingAreas/delete?bettingAreaId=" + id,
			success : function(result) {

				alert('Deleted!');
				window.location = '/bettingAreas/list';
			},
			error : function(result) {
				alert('Cannot delete!');
			}
		});

	}
}

function addRow() {
	var empTab = document.getElementById("notesTable");

	var rowCnt = empTab.rows.length;        // GET TABLE ROW COUNT.
	var colCnt = 3;
	var tr = empTab.insertRow(rowCnt);      // TABLE ROW.
	tr = empTab.insertRow(rowCnt);

	for (var c = 0; c < colCnt; c++) {
		var td = document.createElement('td');          // TABLE DEFINITION.
		td = tr.insertCell(c);

		if (c == 0) {           // FIRST COLUMN.
			// ADD A BUTTON.
			var removeButton = document.createElement('input');

			// SET INPUT ATTRIBUTE.
			removeButton.setAttribute('type', 'button');
			removeButton.setAttribute('value', 'Remove');
			removeButton.setAttribute('class', 'btn btn-primary btn-sm');
			removeButton.setAttribute('style', 'margin-bottom : 5px;');

			// ADD THE BUTTON's 'onclick' EVENT.
			removeButton.setAttribute('onclick', 'removeRow(this)');

			td.appendChild(removeButton);

			var saveButton = document.createElement('a');
			saveButton.setAttribute('class', 'btn btn-primary btn-sm');
			saveButton.setAttribute('style', 'color : white; cursor : pointer;');
			saveButton.setAttribute('onclick', 'submit()');
			saveButton.setAttribute('id', 'notes-save-button');
	
			var v = document.createTextNode('Save');
			saveButton.appendChild(v);

			td.appendChild(saveButton);
		}
		else {
			// CREATE AND ADD TEXTBOX IN EACH CELL.
			var ele = document.createElement('input');
			ele.setAttribute('type', 'text');
			ele.setAttribute('value', '');
			
			switch(c) {
			case 1:
				var div = document.createElement('div');
				
				var today = new Date();
				var dd = today.getDate();
				var mm = today.getMonth()+1;
				var yyyy = today.getFullYear();
				var todayFormatted = mm + '-' + dd + '-' + yyyy;
				console.log('todayFormatted : ' + todayFormatted);
				
				document.getElementById('notes-date-input').value = todayFormatted;
				
				console.log(document.getElementById('blockOfHtml').innerHTML);
								
				div.innerHTML = document.getElementById('blockOfHtml').innerHTML;

				td.appendChild(div)
				break;
			case 2:
				ele.setAttribute('style', 'width : 100%;');
				ele.setAttribute('id', 'notes-add-new-input');

				td.appendChild(ele);
				break;
			
			}
		}
	}
}

function removeRow(oButton, id) {
	var empTab = document.getElementById('notesTable');
	empTab.deleteRow(oButton.parentNode.parentNode.rowIndex);
	
	$.ajax({
		
		type: 'POST',
		url : '/machines/deleteNotes?noteId=' + id,
		success : function(data) {
			alert('Note deleted successfully');
		},
		error : function(data) {
			alert('Cannot delete note!');
		}
		
		
	});
}

// EXTRACT AND SUBMIT TABLE DATA.
function submit() {
	console.log('submit');
	var myTab = document.getElementById('notesTable');
	var values = new Array();

	// LOOP THROUGH EACH ROW OF THE TABLE.
	for (row = 1; row < myTab.rows.length - 1; row++) {
		for (c = 1; c < myTab.rows[row].cells.length; c++) {   // EACH CELL
			// IN A ROW.

			var element = myTab.rows.item(row).cells[c];
			var child = element.childNodes[0];

			if (child.nodeType == 1) {

				if(c == 1) { // we're in the date selector cell
					var t = $("#notes-date-input");
					console.log('notes date input : ' + t.val());
					values.push("" + t.val() + "");
				} else {
					var t = element.childNodes[0];
					values.push("" + element.childNodes[0].value + "");
				}


			} else {
				console.log('node Type is not 1');
			}
		}
	}

	var mId = document.getElementById('notes-modal-machine-id').getAttribute('value');

	$.ajax({
		type : 'POST',
		url : '/machines/addNotes?date=' + values[0] + '&note=' + values[1] + '&machineId=' + mId,
		success : function(data) {
			window.location = '/bettingAreas/list';

		},
		error : function(data) {
			alert('Notes cannot be null!');
		}


	});


}

function createEmptyNotesTable() {
	console.log('createEmptyNotesTable');
	var arrHead = new Array();
	arrHead = ['', 'Date', 'Note']; 

	var emptyTable = document.createElement('table');
	emptyTable.setAttribute('id', 'notesTable');

	var tr = emptyTable.insertRow(-1);

	for (var h = 0; h < arrHead.length; h++) {
		var th = document.createElement('th');          // TABLE HEADER.
		th.innerHTML = arrHead[h];
		tr.appendChild(th);
	}

	var div = document.getElementById('cont');
	div.appendChild(emptyTable); 
}



