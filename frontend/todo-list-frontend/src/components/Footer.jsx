import React from 'react';
import logo from '../assets/logo_puc.png'; // Caminho para a logo

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-content">
        <div className="footer-text">
          <p>Autor: Alberto da Costa Reis Júnior</p>
          <p>Disciplina: Laboratório de Desenvolvimento de Software</p>
        </div>
        <div className="footer-logo">
          <img src={logo} alt="Logo PUC" />
        </div>
      </div>
    </footer>
  );
};

export default Footer;