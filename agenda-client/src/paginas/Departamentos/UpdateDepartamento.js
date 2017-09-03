import React from 'react';
import Select from 'react-select';
import axios from 'axios';
import { Button, Modal, FormGroup, ControlLabel, FormControl } from 'react-bootstrap';

var UpdateDepartamento = React.createClass({

	getInitialState: function() {
		return {
			updateObject: {
				id: '', 
				nombre: '',
				descripcion: ''
			}
		}
    },

	render: function() {

		return (
			<Modal show={this.props.parent.state.showUpdateModal}>
				<Modal.Header>
					<Modal.Title>Actualizar Departamento</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<form>
						<FormGroup>
							<ControlLabel>Nombre de Departamento</ControlLabel>
							<FormControl
								type="text"
								placeholder="Ingrese Nombre"
								value={this.state.updateObject.nombre}
								onChange={this.onUpdateDepartamentoNombreChange} />
							<br />
							
							<ControlLabel>Descripcion</ControlLabel>
							<FormControl
								type="text"
								placeholder="Ingrese Descripcion"
								value={this.state.updateObject.description}
								onChange={this.onUpdateDepartamentoDescripcionChange} />
							<br />
						</FormGroup>
					</form>						
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.parent.closeUpdateModal}>Cerrar</Button>
					<Button bsStyle="primary" onClick={this.onUpdateBtnClicked}>Actualizar</Button>
				</Modal.Footer>
			</Modal>
		);
	},

	fillUpdateObject: function() {
    	var selectedDepartamento = this.props.parent.getDepartamentoById(this.props.parent.state.selectedDepartamentoId);

		this.state.updateObject = {
			id: selectedDepartamento.id,
			name: selectedDepartamento.nombre,
			description: selectedDepartamento.descripcion
		}
	},
	clearUpdateObject: function() {
		this.state.updateObject.id = '';
		this.state.updateObject.nombre = '';
		this.state.updateObject.descripcion = '';
	},

	//Input changes
	onUpdateDepartamentoNombreChange: function(event) {
		this.state.updateObject.nombre = event.target.value;
		this.forceUpdate();
	},
	onUpdateDepartamentoDescripcionChange: function(event) {
		this.state.updateObject.descripcion = event.target.value;
		this.forceUpdate();
	},	
	onUpdateBtnClicked: function() {
		
		//Update Department
		axios.put('http://localhost:8080/departamentos/' + this.state.updateObject.id, this.state.updateObject)
			.then(function (response) {
				this.props.parent.closeUpdateModal();
				this.props.parent.refreshTable();
				console.log(response);
			}.bind(this))
			.catch(function (error) {
				console.log(error);
			});
	}
});

export default UpdateDepartamento;