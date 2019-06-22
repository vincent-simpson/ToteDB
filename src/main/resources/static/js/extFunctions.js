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
			$("#bettingAreaModalHolder #add-bettingArea-title").html(
					"Edit Betting Area");

			$("#addBettingAreaForm").modal("show");

		}
	});
};

function bindParentButtonText(id) {

	console.log('id ' + id);

	if (id == 0) {
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
		type : 'POST',
		url : '/machines/addNotes/bind?machineId=' + id,
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
	if (window.confirm("Are you sure?")) {

		// need to check if notes input and date haven't been edited. if so,
		// delete without sending ajax request.

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

function addRow(isSubmittedRow, date, note) {
	var empTab = document.getElementById("notesTable");
	console.log("issubmittedrow = " + isSubmittedRow);

	var rowCnt = empTab.rows.length; // GET TABLE ROW COUNT.
	var colCnt = 3;
	var tr;
	tr = empTab.insertRow(-1); // TABLE ROW.


	for (var c = 0; c < colCnt; c++) {
		var td = document.createElement('td'); // TABLE DEFINITION.
		td = tr.insertCell(c);

		if (c == 0) { // FIRST COLUMN.
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
			saveButton
					.setAttribute('style', 'color : white; cursor : pointer;');
			saveButton.setAttribute('onclick', 'submit()');
			saveButton.setAttribute('id', 'notes-save-button');

			var v = document.createTextNode('Save');
			saveButton.appendChild(v);

			td.appendChild(saveButton);
		} else {
			// CREATE AND ADD TEXTBOX IN EACH CELL.
			switch (c) {
			case 1:
				var blockOfHtml = document.getElementById('blockOfHtml');
				var x;

				if (isSubmittedRow) {
					console.log('date is = ' + date);
					x = '<div class="form-group row"> <div class="col-10"> <td>' + date + '</td> </div> </div>';
				} else {
					var today = new Date();
					var dd = today.getDate();
					var mm = today.getMonth() + 1;
					var yyyy = today.getFullYear();

					if (mm < 10) {
						mm = "0" + mm;
					}

					var todayFormatted = mm + '-' + dd + '-' + yyyy;
					console.log('todayFormatted : ' + todayFormatted);

					x = '<div class="form-group row"> <div class="col-10"> <input class="form-control" type="text" id="notes-date-input" value="'
							+ todayFormatted + '">' + '<small>Enter as Month/Day/Year</small></div> </div>';
				}

				blockOfHtml.innerHTML = x;

				console.log('blockOfHtml inner HTML' + blockOfHtml.innerHTML);

				blockOfHtml.setAttribute('style', 'hidden : false;');

				td.appendChild(blockOfHtml);
				break;
			case 2:
				var ele;
				if(!isSubmittedRow) {
					ele = document.createElement('input');
					ele.setAttribute('type', 'text');
					ele.setAttribute('value', '');
					ele.setAttribute('style', 'width : 100%;');
					ele.setAttribute('id', 'notes-add-new-input');
				} else {
					ele = document.createElement('td');
					ele.setAttribute('style', 'border-style: hidden;');
					ele.innerHTML = "<td>" + note + "</td>";
				}
				

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

		type : 'POST',
		url : '/machines/deleteNotes?noteId=' + id,
		success : function(data) {
		},
		error : function(data) {
			alert('Cannot delete note!');
		}

	});
}

// EXTRACT AND SUBMIT TABLE DATA.
function submit() {
	var myTab = document.getElementById('notesTable');
	var values = new Array();

	// LOOP THROUGH EACH ROW OF THE TABLE.

	var t = document.getElementById('notes-date-input');
	console.log('notes date input : ' + t.value);
	values.push("" + t.value + "");
	console.log('values push date input : ' + t.value);
	
	var notes = document.getElementById('notes-add-new-input');
	values.push("" + notes.value + "");

	var mId = document.getElementById('notes-modal-machine-id').getAttribute(
			'value');

	$.ajax({
		type : 'POST',
		url : '/machines/addNotes?date=' + values[0] + '&note=' + values[1]
				+ '&machineId=' + mId,
		dataType : 'json',
		success : function(data) {
			console.log("add notes success data: " + data);
			// window.location = '/bettingAreas/list';
			addRow(true, values[0], values[1]);

		},
		error : function(data) {
			alert('Notes cannot be null!');
		}

	});

}

function createEmptyNotesTable() {
	console.log('createEmptyNotesTable');
	var arrHead = new Array();
	arrHead = [ '', 'Date', 'Note' ];

	var emptyTable = document.createElement('table');
	emptyTable.setAttribute('id', 'notesTable');

	var tr = emptyTable.insertRow(-1);

	for (var h = 0; h < arrHead.length; h++) {
		var th = document.createElement('th'); // TABLE HEADER.
		th.innerHTML = arrHead[h];
		tr.appendChild(th);
	}

	var div = document.getElementById('cont');
	div.appendChild(emptyTable);
}
