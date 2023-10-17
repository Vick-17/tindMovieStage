import React, { useState } from "react";

const MessageBox = () => {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");

  const sendMessage = () => {
    if (input.trim() !== "") {
      setMessages([...messages, input]);
      setInput("");
    }
  };

  return (
    <div>
      <MessageList messages={messages} />
      <SendMessageForm
        input={input}
        setInput={setInput}
        sendMessage={sendMessage}
      />
    </div>
  );
};

const MessageList = ({ messages }) => {
  return (
    <div className="message_container">
      {messages.map((message, index) => (
        <div className="my_message" key={index}>
          <p>{message}</p>
        </div>
      ))}
    </div>
  );
};

const SendMessageForm = ({ input, setInput, sendMessage }) => {
  const handleChange = (e) => {
    setInput(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    sendMessage();
  };

  return (
    <form className="send_message" onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="envoyer un message"
        value={input}
        onChange={handleChange}
      />
      <button type="submit">
        <svg
          width="22px"
          height="22px"
          viewBox="0 0 24 24"
          strokeWidth="1"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
          color="#fff"
        >
          <g
            clipPath="url(#send-diagonal_svg__clip0_2476_13290)"
            stroke="#fff"
            strokeWidth="1"
            strokeLinecap="round"
            strokeLinejoin="round"
          >
            <path d="M22.152 3.553L11.178 21.004l-1.67-8.596L2 7.898l20.152-4.345zM9.456 12.444l12.696-8.89"></path>
          </g>
          <defs>
            <clipPath id="send-diagonal_svg__clip0_2476_13290">
              <path fill="#fff" d="M0 0h24v24H0z"></path>
            </clipPath>
          </defs>
        </svg>
      </button>
    </form>
  );
};

export default MessageBox;
