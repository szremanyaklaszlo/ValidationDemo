# Input/DTO validation in Kotlin

This demo shows a very clean, concise implementation of input/DTO validation in Kotlin.
This is a native solution, doesn’t require any dependencies, and is completely free from annotations.

For testing, I use Kotest and Mockk.

## Possible modifications for your needs:

- ### Remove context.throwIfAny() from the validate function and return the ValidationContext <br>
  This way, you can capture the context/errors without throwing an exception. It can be useful if you want to log the errors beforehand, or you just don’t want to throw an exception at all. <br>
  
- ### Group errors by fieldName <br>
  Update the errors data structure to MutableMap<String, MutableList<String>> and update the operations to append errors if the fields already exist in the map. Combined with the previous modification, it can be a potential use case for form validation. <br>
