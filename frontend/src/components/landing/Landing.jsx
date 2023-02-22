import React from "react";
import { useNavigate } from "react-router-dom";
import "./landingStyle.css";

export default function Landing() {
  const navigate = useNavigate();
  return (
    <div class="container">
      <div class="logo-holder logo-5">
        <h3>UPBEAT</h3>
      </div>

      <div className="btn-container">
        <button
          class="button-27"
          role="button"
          onClick={() => navigate("/map")}
        >
          START
        </button>
        <button class="button-27" role="button">
          HOW TO PLAY
        </button>
        <button class="button-27" role="button">
          SETTING
        </button>
      </div>
    </div>
  );
}
