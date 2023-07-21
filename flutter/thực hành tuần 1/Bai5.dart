import 'package:flutter/material.dart';

void main() {
  runApp(CalculatorApp());
}

class CalculatorApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Prime Number Checker',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: PrimeCheckerScreen(),
    );
  }
}

class PrimeCheckerScreen extends StatefulWidget {
  @override
  _PrimeCheckerScreenState createState() => _PrimeCheckerScreenState();
}

class _PrimeCheckerScreenState extends State<PrimeCheckerScreen> {
  TextEditingController _numberController = TextEditingController();
  bool _isPrime = false;

  void _checkPrime() {
    int number = int.tryParse(_numberController.text) ?? 0;

    if (isPrime(number)) {
      setState(() {
        _isPrime = true;
      });
    } else {
      setState(() {
        _isPrime = false;
      });
    }
  }

  bool isPrime(int number) {
    if (number <= 1) return false;
    for (int i = 2; i <= (number / 2).floor(); i++) {
      if (number % i == 0) {
        return false;
      }
    }
    return true;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Prime Number Checker'),
      ),
      body: Center(
        child: Padding(
          padding: EdgeInsets.all(20.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              TextField(
                controller: _numberController,
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                  labelText: 'Enter a number',
                ),
              ),
              SizedBox(height: 16.0),
              ElevatedButton(
                onPressed: _checkPrime,
                child: Text('Check Prime'),
              ),
              SizedBox(height: 16.0),
              _isPrime
                  ? Text('The number is a prime number.')
                  : Text('The number is not a prime number.'),
            ],
          ),
        ),
      ),
    );
  }
}
