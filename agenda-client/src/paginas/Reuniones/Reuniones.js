import React from 'react';
import Select from 'react-select';
import axios from 'axios';
import { ButtonGroup, Button, Modal, Glyphicon, FormGroup, ControlLabel, FormControl } from 'react-bootstrap';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';

import AddReunionModal from './AddReunion';
import UpdateReunionModal from './UpdateReunion';

var Reuniones = React.createClass({

	getInitialState: function() {

		return {
			data: null,
			departamentos: null,
			selectedReunionId: null,
			showAddModal: false,
			showUpdateModal: false
		}
    },
	
	componentDidMount: function() {
		this.refreshTable();
	},
	
	render: function() {

		var selectRowProp = {
			mode: "radio",
			clickToSelect: true,
			className: "selected-row",
			bgColor: 'rgb(101, 148, 255)',
			onSelect: this.onRowSelect
		};		
		
		if(!this.state.data){
			return (<div></div>);
		}
		
		return (
			<div>
				<ButtonGroup className="m-10">
					<Button bsStyle="primary" onClick={this.openAddModal}><Glyphicon glyph="plus" />Add</Button>
					<Button bsStyle="warning" disabled={this.state.selectedReunionId === null} onClick={this.openUpdateModal}><Glyphicon glyph="refresh" />Actualizar</Button>
					<Button bsStyle="danger" disabled={this.state.selectedReunionId === null} onClick={this.onDeleteBtnClicked}><Glyphicon glyph="trash" />Eliminar</Button>
				</ButtonGroup>
			
				<BootstrapTable data={this.state.data} 
								striped={true} 
								hover={true}
								search={true} 
								selectRow={selectRowProp}>
					<TableHeaderColumn dataField="id" isKey={true} dataAlign="center" dataSort={true}>Reunion ID</TableHeaderColumn>
					<TableHeaderColumn dataField="nombre" dataSort={true}>Nombre</TableHeaderColumn>
					<TableHeaderColumn dataField="descripcion">Descripcion</TableHeaderColumn>
				</BootstrapTable>
							
				<AddReunionModal parent={this} ref="addReunion" />

				<UpdateReunionModal parent={this} ref="updateReunion"/>
			</div>		
		);
	},
	
	// Keep selected row
	onRowSelect: function(row, isSelected) {
		if(isSelected) {
			this.setState({ selectedReunionId: row.id });
		}else {
			this.setState({ selectedReunionId: null });
		}
	},
	
	// Department list for Select component
	getDepartamentoOptions: function(departamentos) {
		var options = [];
		
		if(!departamentos) {
			return options;
		}

		options = departamentos.map(function(obj){
			var rObj = {};
			rObj['value'] = obj['id'];
			rObj['label'] = obj['nombre'];
			return rObj;
		});

		return options;		
	},	
	
	//Add modal open/close
	closeAddModal: function() {
		this.setState({ showAddModal: false });
		this.refs.addReunion.clearAddObject();
	},

	openAddModal: function() {
		this.refs.addReunion.clearAddObject();
		this.setState({ showAddModal: true });
	},

	//Update modal open/close
	closeUpdateModal: function() {
		this.setState({showUpdateModal: false});
		this.refs.updateReunion.clearUpdateObject();
	},

	openUpdateModal: function() {
		this.refs.updateReunion.fillUpdateObject();
		this.setState({showUpdateModal: true});
	},

	//BEGIN: Delete Reunion
	onDeleteBtnClicked: function() {
		
		axios.delete('http://localhost:8080/reuniones/' + this.state.selectedReunionId)
			.then(function (response) {
				this.refreshTable();
			}.bind(this))
			.catch(function (error) {
				console.log(error);
			});		
	},
	//END: Delete Reunion
	
	getReunionById: function(id) {
		
		for(var i in this.state.data) {
			if(this.state.data[i].id === id) {
				return this.state.data[i];
			}
		}
		return '';
	},


	getReuniones: function() {
	  return axios.get('http://localhost:8080/reuniones');
	},

	getDepartamentos: function() {
	  return axios.get('http://localhost:8080/departamentos');
	},
	
	//Get table data and update the state to render
	refreshTable: function() {
		
		axios.all([this.getReuniones(), this.getDepartamentos()])
		.then(axios.spread(function (reuniones, departamentos) {
			this.setState({data: reuniones.data,
							departments: departamentos.data});
		}.bind(this)));
	}
});

export default Reuniones;