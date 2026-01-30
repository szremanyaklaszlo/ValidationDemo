# Input/DTO validation demo in Kotlin

This demo shows a very clean, consice implementation of input/DTO validation in Kotlin.
This is a native solution, doesn't require any dependency and completely free from annotations.

For testing I use Kotest and Mockk.

## Possible modifications for your needs:

- ### Remove context.throwIfAny() from the validate function and return the ValidationContext. <br>
  This way you can capture the context/errors without throwing an exeption.
  It can be usefull, if you want to log the errors before throwing exception.
  Or if you just don't want to throw error at all. <br>
  
- ### Group the errors by fieldName. <br>
  Update errors data structure to MutableMap<String, MutableList<String>> and update the operations to append errors if the fields already exist in the map.
  Combined with the previous modification, it can be a potential usecase for form validation. <br>
