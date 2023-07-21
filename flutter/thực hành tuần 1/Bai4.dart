import 'package:flutter/material.dart';

void main() {
  runApp(CalculatorApp());
}

class CalculatorApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Calculator(),
    );
  }
}

class Calculator extends StatefulWidget {
  @override
  _CalculatorState createState() => _CalculatorState();
}

class _CalculatorState extends State<Calculator> {
  String _output = '';
  double _result = 0.0;
  String _operator = '';
  bool _isOperatorPressed = false;

  void _onNumberPressed(String number) {
    setState(() {
      if (_isOperatorPressed) {
        _output = number;
        _isOperatorPressed = false;
      } else {
        _output += number;
      }
    });
  }

  void _onOperatorPressed(String operator) {
    setState(() {
      if (_operator.isNotEmpty) {
        _calculateResult();
        _output = _result.toString();
      } else {
        _result = double.parse(_output);
      }
      _operator = operator;
      _isOperatorPressed = true;
    });
  }

  void _onEqualsPressed() {
    setState(() {
      _calculateResult();
      _output = _result.toString();
      _operator = '';
    });
  }

  void _calculateResult() {
    double number = double.parse(_output);
    switch (_operator) {
      case '+':
        _result += number;
        break;
      case '-':
        _result -= number;
        break;
      case '*':
        _result *= number;
        break;
      case '/':
        _result /= number;
        break;
    }
  }

  void _onClearPressed() {
    setState(() {
      _output = '';
      _result = 0.0;
      _operator = '';
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Calculator'),
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: <Widget>[
          Expanded(
            child: Container(
              padding: EdgeInsets.all(16.0),
              alignment: Alignment.bottomRight,
              child: Text(
                _output,
                style: TextStyle(fontSize: 48.0),
              ),
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              _buildNumberButton('7'),
              _buildNumberButton('8'),
              _buildNumberButton('9'),
              _buildOperatorButton('/'),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              _buildNumberButton('4'),
              _buildNumberButton('5'),
              _buildNumberButton('6'),
              _buildOperatorButton('*'),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              _buildNumberButton('1'),
              _buildNumberButton('2'),
              _buildNumberButton('3'),
              _buildOperatorButton('-'),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              _buildNumberButton('0'),
              _buildClearButton(),
              _buildEqualsButton(),
              _buildOperatorButton('+'),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildNumberButton(String number) {
    return Expanded(
      child: ElevatedButton(
        onPressed: () => _onNumberPressed(number),
        child: Text(number, style: TextStyle(fontSize: 24.0)),
      ),
    );
  }

  Widget _buildOperatorButton(String operator) {
    return Expanded(
      child: ElevatedButton(
        onPressed: () => _onOperatorPressed(operator),
        child: Text(operator, style: TextStyle(fontSize: 24.0)),
      ),
    );
  }

  Widget _buildEqualsButton() {
    return Expanded(
      child: ElevatedButton(
        onPressed: _onEqualsPressed,
        child: Text('=', style: TextStyle(fontSize: 24.0)),
      ),
    );
  }

  Widget _buildClearButton() {
    return Expanded(
      child: ElevatedButton(
        onPressed: _onClearPressed,
        child: Text('C', style: TextStyle(fontSize: 24.0)),
      ),
    );
  }
}
