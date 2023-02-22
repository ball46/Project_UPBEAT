import React, { useState } from "react";
import "./inputStyle.css";

function InputName() {
  const [name1, setName1] = useState("");
  const [name2, setName2] = useState("");

  return (
    <div className="input-container">
      {/* Player1 */}
      <div class="group">
        <input
          required=""
          type="text"
          class="input"
          placeholder="Player 1"
          onChange={(e) => setName1(e.target.value)}
        />
        <span class="bar-1"></span>
        {name1}
      </div>

      {/* Player2 */}
      <div class="group">
        <input
          required=""
          type="text"
          class="input"
          placeholder="Player 2"
          onChange={(e) => setName2(e.target.value)}
        />
        <span class="bar-2"></span>
        {name2}
      </div>

      <button class="start-btn" role="button" onClick={() => navigate("")}>
        START
      </button>
    </div>
  );
}

export default InputName;
