import React from 'react';
import Select from 'react-select';
import axios from 'axios';
import { Button, Modal, FormGroup, ControlLabel, FormControl } from 'react-bootstrap';

var UpdateReunion = React.createClass({

	getInitialState: function() {

		return {
			updateObject: {
				id: '', 
				nombre: '',
				descripcion: '',
				departamentos: ''
			}
		}
    },

	render: function() {
		
		if(this.props.parent.state.showUpdateModal === false){
			return (<div></div>);
		}

		return (
			<Modal show={this.props.parent.state.showUpdateModal}>
				<Modal.Header>
					<Modal.Title>Actualizar Reunion</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<form>
						<FormGroup>
							<ControlLabel>Nombre de Reunion</ControlLabel>
							<FormControl
								type="text"
								placeholder="Ingrese nombre"
								value={this.state.updateObject.nombre}
								onChange={this.onUpdateReunionNombreChange} />
							<br />
							
							<ControlLabel>Descripcion</ControlLabel>
							<FormControl
								type="text"
								placeholder="Descripcion"
								value={this.state.updateObject.descripcion}
								onChange={this.onUpdateReunionDescripcionChange} />
							<br />
							
							<ControlLabel>Departamentos</ControlLabel>
							<Select
								name="departamentosField"
								multi={true}
								value={this.props.parent.getDepartamentoOptions(this.state.updateObject.departamentos)}
								options={this.props.parent.getDepartamentoOptions(this.props.parent.state.departamentos)}
								onChange={this.onUpdateReunionDepartamentoChange} />
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

    	var selectedReunion = this.props.parent.getReunionById(this.props.parent.state.selectedReunionId);

		this.state.updateObject = {
			id: selectedReunion.id,
			nombre: selectedReunion.nombre,
			descripcion: selectedReunion.descripcion,
			departamentos: selectedReunion.departamentos,
		}
	},

	clearUpdateObject: function() {
		
		this.state.updateObject.id = '';
		this.state.updateObject.nombre = '';
		this.state.updateObject.descripcion = '';
		this.state.updateObject.departamentos = '';
	},

	//Input changes
	onUpdateReunionNombreChange: function(event) {
		this.state.updateObject.nombre = event.target.value;
		this.forceUpdate();
	},

	onUpdateReunionDescripcionChange: function(event) {
		this.state.updateObject.descripcion = event.target.value;
		this.forceUpdate();
	},

	onUpdateReunionDepartamentoChange: function(selection) {

		if (selection === null) {
			this.state.updateObject.departamentos = null;
		} else {
			var departamentos = selection.map(function(obj){
				var rObj = {};
				rObj['id'] = obj['value'];
				rObj['nombre'] = obj['label'];
				return rObj;
			});
			
			this.state.updateObject.departamentos = departamentos;
		}

		this.forceUpdate();		
	},	

	onUpdateBtnClicked: function() {
		
		//Update Reunion
		axios.put('http://localhost:8080/reuniones/' + this.state.updateObject.id, this.state.updateObject)
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

export default UpdateReunion;