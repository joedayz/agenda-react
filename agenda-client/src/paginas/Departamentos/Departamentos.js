import React from 'react';
import Select from 'react-select';
import axios from 'axios';
import { ButtonGroup, Button, Modal, Glyphicon, FormGroup, ControlLabel, FormControl } from 'react-bootstrap';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';

import AddDepartamentoModal from './AddDepartamento';
import UpdateDepartamentoModal from './UpdateDepartamento';

var Departamentos = React.createClass({

	getInitialState: function() {

		return {
			data: null,
			selectedDepartamentoId: null,
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
					<Button bsStyle="primary" onClick={this.openAddModal}><Glyphicon glyph="plus" />Agregar</Button>
					<Button bsStyle="warning" disabled={this.state.selectedDepartamentoId === null} onClick={this.openUpdateModal}><Glyphicon glyph="refresh" />Actualizar</Button>
					<Button bsStyle="danger" disabled={this.state.selectedDepartamentoId === null} onClick={this.onDeleteBtnClicked}><Glyphicon glyph="trash" />Eliminar</Button>
				</ButtonGroup>
			
				<BootstrapTable data={this.state.data} 
								striped={true} 
								hover={true}
								search={true} 
								selectRow={selectRowProp}>
					<TableHeaderColumn dataField="id" isKey={true} dataAlign="center" dataSort={true}>Departamento ID</TableHeaderColumn>
					<TableHeaderColumn dataField="nombre" dataSort={true}>Nombre</TableHeaderColumn>
					<TableHeaderColumn dataField="descripcion">Descripcion</TableHeaderColumn>
				</BootstrapTable>
							
				<AddDepartamentoModal parent={this} ref="addDepartamento" />

				<UpdateDepartamentoModal parent={this} ref="updateDepartamento"/>
			</div>		
		);
	},
	
	// Keep selected row
	onRowSelect: function(row, isSelected) {

		if(isSelected) {
			this.setState({ selectedDepartamentoId: row.id });
		}else {
			this.setState({ selectedDepartamentoId: null });
		}
	},
	
	//Add modal open/close
	closeAddModal: function() {
		this.setState({ showAddModal: false });
		this.refs.addDepartamento.clearAddObject();
	},

	openAddModal: function() {
		this.refs.addDepartamento.clearAddObject();
		this.setState({ showAddModal: true });
	},

	//Update modal open/close
	closeUpdateModal: function() {
		this.setState({showUpdateModal: false});
		this.refs.updateDepartamento.clearUpdateObject();
	},
	
	openUpdateModal: function() {
		this.refs.updateDepartamento.fillUpdateObject();
		this.setState({showUpdateModal: true});
	},

	//BEGIN: Delete Departamento
	onDeleteBtnClicked: function() {
		
		axios.delete('http://localhost:8080/departamentos/' + this.state.selectedDepartamentoId)
			.then(function (response) {
				this.refreshTable();
			}.bind(this))
			.catch(function (error) {
				console.log(error);
			});		
	},
	//END: Delete Departamento
	
	getDepartamentoById: function(id) {

		for(var i in this.state.data) {
			if(this.state.data[i].id === id) {
				return this.state.data[i];
			}
		}
		return '';
	},

	//Get table data and update the state to render
	refreshTable: function() {
		
		axios.get('http://localhost:8080/departamentos')
		.then(function (departamentos) {
			this.setState({data: departamentos.data});
		}.bind(this));
	}
});

export default Departamentos;