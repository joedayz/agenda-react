import React from 'react';
import axios from 'axios';
import { ButtonGroup, Button, Glyphicon} from 'react-bootstrap';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';

import AddEmpleadoModal from './AddEmpleado';
import UpdateEmpleadoModal from './UpdateEmpleado';

var Empleados = React.createClass({

	getInitialState: function() {
		
		return {
			data: null,
			departamentos: null,
			selectedEmpleadoId: null,
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
					<Button bsStyle="warning" disabled={this.state.selectedEmpleadoId === null} onClick={this.openUpdateModal}><Glyphicon glyph="refresh" />Update</Button>
					<Button bsStyle="danger" disabled={this.state.selectedEmpleadoId === null} onClick={this.onDeleteBtnClicked}><Glyphicon glyph="trash" />Delete</Button>
				</ButtonGroup>
			
				<BootstrapTable data={this.state.data} 
								striped={true} 
								hover={true}
								search={true} 
								selectRow={selectRowProp}>
					<TableHeaderColumn dataField="id" isKey={true} dataAlign="center" dataSort={true}>Empleado ID</TableHeaderColumn>
					<TableHeaderColumn dataField="nombre" dataSort={true}>Nombres</TableHeaderColumn>
					<TableHeaderColumn dataField="apellido">Apellidos</TableHeaderColumn>
					<TableHeaderColumn dataField="salario" dataFormat={this.precioFormatter}>Salario</TableHeaderColumn>
					<TableHeaderColumn dataField="departamentoId" dataFormat={this.departamentoFormatter}>Departamento</TableHeaderColumn>
				</BootstrapTable>
							
				<AddEmpleadoModal parent={this} ref="addEmpleado" />

				<UpdateEmpleadoModal parent={this} ref="updateEmpleado"/>
			</div>		
		);
	},
	
	// Keep selected row
	onRowSelect: function(row, isSelected) {
		if(isSelected) {
			this.setState({ selectedEmpleadoId: row.id });
		}else {
			this.setState({ selectedEmpleadoId: null });
		}
	},
	
	// Department list for Select component
	getDepartamentoOptions: function() {
		var options = [];

		options = this.state.departamentos.map(function(obj){
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
		this.refs.addEmpleado.clearAddObject();
	},
	openAddModal: function() {
		this.refs.addEmpleado.clearAddObject();
		this.setState({ showAddModal: true });
	},

	//Update modal open/close
	closeUpdateModal: function() {
		this.setState({showUpdateModal: false});
		this.refs.updateEmpleado.clearUpdateObject();
	},
	openUpdateModal: function() {
		this.refs.updateEmpleado.fillUpdateObject();
		this.setState({showUpdateModal: true});
	},

	//BEGIN: Delete Empleado
	onDeleteBtnClicked: function() {
		
		axios.delete('http://localhost:8080/empleados/' + this.state.selectedEmpleadoId)
			.then(function (response) {
				this.refreshTable();
			}.bind(this))
			.catch(function (error) {
				console.log(error);
			});		
	},
	//END: Delete Empleado
	
	precioFormatter: function(cell, row){
		return '<i class="glyphicon glyphicon-usd"></i> ' + cell;
	},
	
	departamentoFormatter: function(cell, row) {
		return this.getDepartamentoNombre(row.departamentoId);
	},

    getDepartamentoNombre: function(departamentoId) {

		for(var i in this.state.departamentos) {
			if(this.state.departamentos[i].id === departamentoId) {
				return this.state.departamentos[i].nombre;
			}
		}
		return '';
	},

	getEmpleadoById: function(id) {
		for(var i in this.state.data) {
			if(this.state.data[i].id === id) {
				return this.state.data[i];
			}
		}
		return '';
	},

	getEmpleados: function() {
	  return axios.get('http://localhost:8080/empleados');
	},

	getDepartamentos: function() {
	  return axios.get('http://localhost:8080/departamentos');
	},
	
	//Get table data and update the state to render
	refreshTable: function() {
		
		axios.all([this.getEmpleados(), this.getDepartamentos()])
		.then(axios.spread(function (empleados, departamentos) {
			this.setState({data: empleados.data,
                departamentos: departamentos.data});
		}.bind(this)));
	}
});

export default Empleados;